package ua.kuzjka.kumarchaeo.parsing;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.kuzjka.kumarchaeo.dto.ItemParsingDto;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Deformation;
import ua.kuzjka.kumarchaeo.model.Location;
import ua.kuzjka.kumarchaeo.model.PointNumber;
import ua.kuzjka.kumarchaeo.repository.CategoryRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ItemListParser {

    private static final Logger logger = LoggerFactory.getLogger(ItemListParser.class);

    private static final Pattern hectarPattern = Pattern.compile("Га (\\d+)");
    private static final Pattern intPattern = Pattern.compile("\\d+");

    private CsvSchema schema = CsvSchema.builder()
            .addColumn("number")
            .addColumn("name")
            .addColumn("dimensions")
            .addColumn("weight", CsvSchema.ColumnType.NUMBER)
            .addColumn("caliber")
            .addColumn("remark1")
            .addColumn("latitude", CsvSchema.ColumnType.NUMBER)
            .addColumn("longitude", CsvSchema.ColumnType.NUMBER)
            .addColumn("gpsPoint")
            .addColumn("route")
            .addColumn("remark1")
            .addColumn("remark2")
            .setColumnSeparator('\t')
            .build();

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    private Category bulletCategory;

    private ObjectMapper mapper;
    private ObjectMapper mapperCommasSeparator;

    public ItemListParser(@Autowired CategoryRepository categoryRepository,
                          @Autowired ItemRepository itemRepository,
                          @Value("Кулі") String bulletCategoryName) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        Optional<Category> bulletsCategory = categoryRepository.findByName(bulletCategoryName);
        if (bulletsCategory.isEmpty()) throw new IllegalStateException("Bullets category not found");
        this.bulletCategory = bulletsCategory.get();
        mapper = new CsvMapper();
        mapperCommasSeparator = new CsvMapper().registerModule(new CommaFloatJacksonModule());
    }

    public List<ItemParsingDto> parseCsv(InputStream in) throws IOException {
        return parseCsv(in, false);
    }

    public List<ItemParsingDto> parseCsv(String str) throws IOException {
        return parseCsv(str, false);
    }

    public List<ItemParsingDto> parseCsv(InputStream in, boolean commaDecimalSeparators) throws IOException {
        ObjectMapper mapper = commaDecimalSeparators ? this.mapperCommasSeparator : this.mapper;

        try (MappingIterator<ItemListEntry> it = mapper.readerFor(ItemListEntry.class).with(schema).readValues(in)) {
            List<ItemListEntry> entries = it.readAll();

            return entries.stream().map(this::parseItem).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public List<ItemParsingDto> parseCsv(String str, boolean commaDecimalSeparators) throws IOException {
        ObjectMapper mapper = commaDecimalSeparators ? this.mapperCommasSeparator : this.mapper;

        try (MappingIterator<ItemListEntry> it = mapper.readerFor(ItemListEntry.class).with(schema).readValues(str)) {
            List<ItemListEntry> entries = it.readAll();

            return entries.stream().map(this::parseItem).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    public ItemParsingDto parseItem(ItemListEntry entry) {
        PointNumber pointNumber;
        try {
            pointNumber = PointNumber.parseNumber(entry.getNumber());
        } catch (IllegalArgumentException e) {
            logger.warn("Couldn't parse point number: " + entry.getNumber());
            return null;
        }

        ItemParsingDto item = new ItemParsingDto();
        item.setName(entry.getName());
        item.setNumber(PointNumber.parseNumber(entry.getNumber()));
        item.setDimensions(entry.getDimensions());
        item.setWeight(entry.getWeight());
        item.setLocation(new Location(entry.getLatitude(), entry.getLongitude()));

        List<String> remarks = new ArrayList<>();
        if (entry.getGpsPoint() != null) remarks.add("GPS point: " + entry.getGpsPoint());
        if (entry.getRoute() != null) remarks.add("Route: " + entry.getRoute());
        if (entry.getRemark1() != null) remarks.add(entry.getRemark1());
        if (entry.getRemark2() != null) remarks.add(entry.getRemark2());
        if (!remarks.isEmpty()) item.setRemarks(String.join(", ", remarks));

        int hectar = autodetectHectar(entry.getRoute());
        if (hectar != -1) {
            item.setHectare(hectar);
            item.setHectareAutodetected(true);
        }

        item.setGpsPoint(entry.getGpsPoint());

        Category category = autodetectCategory(item.getName());
        if (category != null) {
            item.setCategory(category.getName());
            item.setCategoryAutodetected(true);
            if (category.getId().equals(bulletCategory.getId())) {
                Deformation deformation = autodetectDeformed(entry.getName());
                item.setDeformation(deformation);
                if (deformation != Deformation.NONE) {
                    item.setDeformationAutodetected(true);
                }

                int caliber = -1;
                if (entry.getCaliber() != null) {
                    caliber = parseCaliber(entry.getCaliber());
                    item.setCaliberApproximate(caliber != -1 && parseIsApproximate(entry.getCaliber()));
                }
                if (caliber == -1 && deformation != Deformation.HARD) {
                    caliber = autodetectCaliber(entry.getDimensions());
                    item.setCaliberAutodetected(caliber != -1);
                }

                if (caliber != -1) {
                    item.setCaliber(caliber);
                }
            }
        }

        if (itemRepository.findByPointNumber(pointNumber).isPresent()) {
            item.setNumberExists(true);
            item.setSave(false);
        } else {
            item.setSave(true);
        }

        return item;
    }

    /**
     * Autodetects item category from name.
     * @param name  Item name
     * @return      Detected category or {@code null} if not detected
     */
    public Category autodetectCategory(String name) {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            for (String filter : category.getFilters()) {
                if (name.toLowerCase().contains(filter.toLowerCase())) return category;
            }
        }
        return null;
    }

    /**
     * Autodetects bullet caliber from dimensions.
     * @param dimensions    Bullet dimensions
     * @return              Bullet caliber or {@code -1} if not detected
     */
    public int autodetectCaliber(String dimensions) {
        try {
            int[] numbers = Arrays.stream(dimensions.split("[XxХх]")).mapToInt(Integer::parseInt).toArray();
            switch(numbers.length) {
                case 2:
                    return numbers[0];
                case 3:
                    return Math.max(numbers[0], numbers[1]);
                default:
                    return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int parseCaliber(String str) {
        Matcher matcher = intPattern.matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return -1;
    }

    public boolean parseIsApproximate(String str) {
        return str.contains("≈");
    }

    /**
     * Detects if bullet is deformed from item name.
     * @param name  Bullet name
     * @return      {@code true} if deformation detected
     */
    public Deformation autodetectDeformed(String name) {
        String lowercase = name.toLowerCase();
        if (lowercase.contains("деформ")) return Deformation.LIGHT;
        if (lowercase.contains("розплющ")
                || lowercase.contains("розрізан")) return Deformation.HARD;
        return Deformation.NONE;
    }

    /**
     * Detects hectar number from the route remark.
     * @param route Route remark
     * @return      Hectar number or {@code -1} if not detected
     */
    public int autodetectHectar(String route) {
        Matcher matcher = hectarPattern.matcher(route);
        if (matcher.find())
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return -1;
            }
        return -1;
    }
}
