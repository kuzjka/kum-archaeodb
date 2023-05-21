package ua.kuzjka.kumarchaeo.export;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kuzjka.kumarchaeo.model.*;
import ua.kuzjka.kumarchaeo.repository.BulletRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemExportServiceTest {
    private static String[] EXPECTED_ITEM_COLUMNS;
    private static String[] EXPECTED_BULLET_COLUMNS;

    private ItemRepository itemRepository;
    private BulletRepository bulletRepository;

    private ItemExportService itemExportService;

    private Random random;
    private char[] alphabet;

    @BeforeAll
    public static void setColumnExpectations() {
        EXPECTED_ITEM_COLUMNS = new String[]{"pointNumber", "name", "category", "year", "hectare",
                "depth", "latitude", "longitude", "weight", "context"};
        String[] additionalBulletColumns = new String[]{"caliber", "approximate", "standard", "deformed", "imprints"};
        EXPECTED_BULLET_COLUMNS = Stream.concat(Arrays.stream(EXPECTED_ITEM_COLUMNS), Arrays.stream(additionalBulletColumns))
                .toArray(String[]::new);
    }

    @BeforeEach
    public void setUpMocks() {
        itemRepository = mock(ItemRepository.class);
        bulletRepository = mock(BulletRepository.class);

        itemExportService = new ItemExportService(itemRepository, bulletRepository);
    }

    @BeforeEach
    public void setUpItemGenerator() {
        random = new Random();
        alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    }

    @Test
    public void testExportAllItems() throws IOException {
        when(itemRepository.findAll()).thenReturn(List.of(
                createRandomItem(),
                createRandomItem(),
                createRandomItem()
        ));

        byte[] result = itemExportService.exportItems(null, null);

        String[] lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result)))) {
            lines = br.lines().toArray(String[]::new);
        }

        assertEquals(4, lines.length);
        assertThat(lines[0].split(","), arrayContainingInAnyOrder(EXPECTED_ITEM_COLUMNS));

        verify(itemRepository).findAll();
    }

    @Test
    public void testExportItemsByIds() throws IOException {
        when(itemRepository.findAllByIdIn(anyList())).thenReturn(List.of(
                createRandomItem(),
                createRandomItem()
        ));

        byte[] result = itemExportService.exportItems(List.of(1, 2, 3), null);

        String[] lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result)))) {
            lines = br.lines().toArray(String[]::new);
        }

        assertEquals(3, lines.length);
        assertThat(lines[0].split(","), arrayContainingInAnyOrder(EXPECTED_ITEM_COLUMNS));

        verify(itemRepository).findAllByIdIn(List.of(1, 2, 3));
    }

    @Test
    public void testExportItemsByCategories() throws IOException {
        when(itemRepository.findAllByCategoryNameIn(anyList())).thenReturn(List.of(
                createRandomItem(),
                createRandomItem()
        ));

        byte[] result = itemExportService.exportItems(null, List.of("Cat 1", "Cat 2"));

        String[] lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result)))) {
            lines = br.lines().toArray(String[]::new);
        }

        assertEquals(3, lines.length);
        assertThat(lines[0].split(","), arrayContainingInAnyOrder(EXPECTED_ITEM_COLUMNS));

        verify(itemRepository).findAllByCategoryNameIn(List.of("Cat 1", "Cat 2"));
    }

    @Test
    public void testExportItemsByIdsAndCategories() throws IOException {
        when(itemRepository.findAllByIdInAndCategoryNameIn(anyList(), anyList())).thenReturn(List.of(
                createRandomItem()
        ));

        byte[] result = itemExportService.exportItems(List.of(1, 2, 3), List.of("Cat 1", "Cat 2"));

        String[] lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result)))) {
            lines = br.lines().toArray(String[]::new);
        }

        assertEquals(2, lines.length);
        assertThat(lines[0].split(","), arrayContainingInAnyOrder(EXPECTED_ITEM_COLUMNS));

        verify(itemRepository).findAllByIdInAndCategoryNameIn(List.of(1, 2, 3), List.of("Cat 1", "Cat 2"));
    }

    @Test
    public void testExportAllBullets() throws IOException {
        when(bulletRepository.findAll()).thenReturn(List.of(
                createRandomBullet(),
                createRandomBullet()
        ));

        byte[] result = itemExportService.exportBullets(null);

        String[] lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result)))) {
            lines = br.lines().toArray(String[]::new);
        }

        assertEquals(3, lines.length);
        assertThat(lines[0].split(","), arrayContainingInAnyOrder(EXPECTED_BULLET_COLUMNS));

        verify(bulletRepository).findAll();
    }

    @Test
    public void testExportBulletsByIds() throws IOException {
        when(bulletRepository.findAllByIdIn(anyList())).thenReturn(List.of(
                createRandomBullet()
        ));

        byte[] result = itemExportService.exportBullets(List.of(2, 3, 4));

        String[] lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(result)))) {
            lines = br.lines().toArray(String[]::new);
        }

        assertEquals(2, lines.length);
        assertThat(lines[0].split(","), arrayContainingInAnyOrder(EXPECTED_BULLET_COLUMNS));

        verify(bulletRepository).findAllByIdIn(List.of(2, 3, 4));
    }

    private Item createRandomItem() {
        Item item = new Item();

        item.setId(random.nextInt(100000));
        item.setName(randomString(random.nextInt(3, 10)));
        item.setPointNumber(random.nextBoolean() ? new PointNumber(random.nextInt(1000))
                : new PointNumber(random.nextInt(1000), random.nextInt(20)));
        item.setYear(2022);
        item.setLocation(new Location(random.nextFloat(90.0f), random.nextFloat(180.0f)));
        item.setCategory(new Category(randomString(random.nextInt(3, 10)), null));
        item.setWeight(random.nextFloat(100.0f));

        return item;
    }

    private Bullet createRandomBullet() {
        Bullet bullet = new Bullet();

        bullet.setId(random.nextInt(100000));
        bullet.setName(randomString(random.nextInt(3, 10)));
        bullet.setPointNumber(random.nextBoolean() ? new PointNumber(random.nextInt(1000))
                : new PointNumber(random.nextInt(1000), random.nextInt(20)));
        bullet.setYear(2022);
        bullet.setLocation(new Location(random.nextFloat(90.0f), random.nextFloat(180.0f)));
        bullet.setCategory(new Category(randomString(random.nextInt(3, 10)), null));
        bullet.setWeight(random.nextFloat(100.0f));
        bullet.setCaliber(random.nextInt(8, 20));
        bullet.setApproximate(random.nextBoolean());
        bullet.setImprints(random.nextBoolean());

        return bullet;
    }

    private String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        char firstChar = Character.toUpperCase(alphabet[random.nextInt(alphabet.length)]);
        sb.append(firstChar);
        for (int i = 1; i < length; ++i) {
            sb.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return sb.toString();
    }
}
