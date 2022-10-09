package ua.kuzjka.kumarchaeo.parsing;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.kuzjka.kumarchaeo.dto.ItemParsingDto;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Location;
import ua.kuzjka.kumarchaeo.model.PointNumber;
import ua.kuzjka.kumarchaeo.repository.CategoryRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ItemListParser {

    private static final Pattern hectarPattern = Pattern.compile("Га (\\d+)");
    private CsvSchema schema = CsvSchema.builder()
            .addColumn("number")
            .addColumn("name")
            .addColumn("dimensions")
            .addColumn("weight", CsvSchema.ColumnType.NUMBER)
            .addColumn("photo")
            .addColumn("latitude", CsvSchema.ColumnType.NUMBER)
            .addColumn("longitude", CsvSchema.ColumnType.NUMBER)
            .addColumn("gpsPoint")
            .addColumn("route")
            .addColumn("remark1")
            .addColumn("remark2")
            .setColumnSeparator('\t')
            .build();

    private ObjectMapper mapper = new CsvMapper();

    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    private Category bulletCategory;

    public ItemListParser(@Autowired CategoryRepository categoryRepository,
                          @Autowired ItemRepository itemRepository,
                          @Value("${archaeodb.bulletsCategory}") String bulletCategoryName) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        Optional<Category> bulletsCategory = categoryRepository.findByName(bulletCategoryName);
        if (bulletsCategory.isEmpty()) throw new IllegalStateException("Bullets category not found");
        this.bulletCategory = bulletsCategory.get();
    }

    public List<ItemParsingDto> parseCsv(InputStream in) throws IOException {
        MappingIterator<ItemListEntry> it = mapper.readerFor(ItemListEntry.class).with(schema).readValues(in);
        List<ItemListEntry> entries = it.readAll();

        List<ItemParsingDto> result = new ArrayList<>(entries.size());

        for (ItemListEntry entry : entries) {
            PointNumber pointNumber;
            try {
                pointNumber = PointNumber.parseNumber(entry.getNumber());
            } catch (IllegalArgumentException e) {
                continue;
            }

            ItemParsingDto item = new ItemParsingDto();
            item.setName(entry.getName());
            item.setNumber(entry.getNumber());
            item.setDimensions(entry.getDimensions());
            item.setWeight(entry.getWeight());
            item.setImage(entry.getPhoto());
            item.setLocation(new Location(entry.getLatitude(), entry.getLongitude()));

            List<String> remarks = new ArrayList<>();
            if (entry.getGpsPoint() != null) remarks.add("GPS point: " + entry.getGpsPoint());
            if (entry.getRoute() != null) remarks.add("Route: " + entry.getRoute());
            if (entry.getRemark1() != null) remarks.add(entry.getRemark1());
            if (entry.getRemark2() != null) remarks.add(entry.getRemark2());
            if (!remarks.isEmpty()) item.setRemarks(String.join(", ", remarks));

            Category category = autodetectCategory(item.getName());
            if (category != null) {
                item.setCategory(category.getName());
                item.setAutoDetectedCategory(true);
                if (category.getId().equals(bulletCategory.getId())) {
                    int caliber = autodetectCaliber(entry.getDimensions());
                    if (caliber != -1) {
                        item.setCaliber(caliber);
                        item.setAutoDetectedCaliber(true);
                    }

                    if (autodetectDeformed(entry.getName())) {
                        item.setDeformed(true);
                        item.setAutoDetectedDeformed(true);
                    }
                }
            }

            if (itemRepository.findByPointNumber(pointNumber).isPresent()) {
                item.setNumberExists(true);
                item.setSave(false);
            } else {
                item.setSave(true);
            }

            result.add(item);
        }

        return result;
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

    /**
     * Detects if bullet is deformed from item name.
     * @param name  Bullet name
     * @return      {@code true} if deformation detected
     */
    public boolean autodetectDeformed(String name) {
        String lowercase = name.toLowerCase();
        return lowercase.contains("деформ")
                || lowercase.contains("розплющ")
                || lowercase.contains("розрізан");
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
