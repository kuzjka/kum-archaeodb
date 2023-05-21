package ua.kuzjka.kumarchaeo.export;

import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import ua.kuzjka.kumarchaeo.model.Bullet;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.repository.BulletRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Provides export to CSV to be used in GIS software.
 */
@Service
public class ItemExportService {

    private ItemRepository itemRepository;
    private BulletRepository bulletRepository;

    private CsvMapper mapper;

    private CsvSchema itemSchema;
    private CsvSchema bulletSchema;

    /**
     * Creates the service.
     * @param itemRepository    Item entity repository
     * @param bulletRepository  Bullet entity repository
     */
    public ItemExportService(ItemRepository itemRepository, BulletRepository bulletRepository) {
        this.itemRepository = itemRepository;
        this.bulletRepository = bulletRepository;

        this.mapper = new CsvMapper();
        itemSchema = mapper.schemaFor(ItemExportEntry.class).withHeader();
        bulletSchema = mapper.schemaFor(BulletExportEntry.class).withHeader();
    }

    /**
     * Exports items to CSV file.
     * @param ids           Item IDs to select or {@code null} to select all items
     * @param categories    Item categories to select or {@code null} to select all categories
     * @return              CSV file contents
     */
    public byte[] exportItems(List<Integer> ids, List<String> categories) {
        List<Item> items;
        if (ids == null && categories == null) {
            items = itemRepository.findAll();
        } else if (ids == null) {
            items = itemRepository.findAllByCategoryNameIn(categories);
        } else if (categories == null) {
            items = itemRepository.findAllByIdIn(ids);
        } else {
            items = itemRepository.findAllByIdInAndCategoryNameIn(ids, categories);
        }

        List<ItemExportEntry> entries = items.stream().map(ItemExportEntry::new).toList();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (SequenceWriter seqWriter = mapper.writer(itemSchema).writeValues(baos)) {
                seqWriter.writeAll(entries);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ItemsExportException(e);
        }
    }

    /**
     * Exports bullets to CSV file.
     * @param ids           Item IDs to select or {@code null} to select all bullets
     * @return              CSV file contents
     */
    public byte[] exportBullets(List<Integer> ids) {
        List<Bullet> bullets;

        if (ids == null) {
            bullets = bulletRepository.findAll();
        } else {
            bullets = bulletRepository.findAllByIdIn(ids);
        }

        List<BulletExportEntry> entries = bullets.stream().map(BulletExportEntry::new).toList();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (SequenceWriter seqWriter = mapper.writer(bulletSchema).writeValues(baos)) {
                seqWriter.writeAll(entries);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ItemsExportException(e);
        }
    }
}
