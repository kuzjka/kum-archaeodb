package ua.kuzjka.kumarchaeo.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kuzjka.kumarchaeo.dto.ItemParsingDto;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.PointNumber;
import ua.kuzjka.kumarchaeo.repository.CategoryRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemListParserTest {
    private static final String BULLETS_NAME = "Bullets";

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;
    private ItemListParser parser;

    private Category bulletCategory;

    @BeforeEach
    public void setUp() {
        itemRepository = mock(ItemRepository.class);
        categoryRepository = mock(CategoryRepository.class);

        bulletCategory = new Category(BULLETS_NAME, List.of("Куля"));
        bulletCategory.setId(123);

        when(categoryRepository.findByName(BULLETS_NAME))
                .thenReturn(Optional.of(bulletCategory));
        when(categoryRepository.findAll()).thenReturn(List.of(bulletCategory));

        parser = new ItemListParser(categoryRepository, itemRepository, BULLETS_NAME);
    }

    @Test
    public void testParse() throws IOException {
        when(itemRepository.findByPointNumber(any())).thenReturn(Optional.empty());

        InputStream in = getClass().getResourceAsStream("itemList.csv");
        List<ItemParsingDto> items = parser.parseCsv(in);

        assertEquals(4, items.size());
        ItemParsingDto item;

        item = items.get(0);
        assertEquals("57", item.getNumber());
        assertEquals("Куля свинцева кругла деформована", item.getName());
        assertEquals(49.52142f, item.getLocation().getLatitude());
        assertEquals(31.53917f, item.getLocation().getLongitude());
        assertNull(item.getHectar());
        assertEquals("12х13х11", item.getDimensions());
        assertEquals(10, item.getWeight());
        assertTrue(item.getRemarks().contains("Вільний пошук"));
        assertTrue(item.getRemarks().contains("Галявина північніше 4-х сосен через дорогу"));
        assertTrue(item.getRemarks().contains("557"));
        assertEquals(BULLETS_NAME, item.getCategory());
        assertEquals(13, item.getCaliber());
        assertTrue(item.isDeformed());
        assertFalse(item.isNumberExists());
        assertTrue(item.isAutoDetectedCategory());
        assertTrue(item.isAutoDetectedCaliber());
        assertTrue(item.isAutoDetectedDeformed());
        assertFalse(item.isAutoDetectedHectar());
        assertTrue(item.isSave());

        item = items.get(2);
        assertEquals("59", item.getNumber());
        assertEquals("Вудила залізні (половина)", item.getName());
        assertEquals(49.52083f, item.getLocation().getLatitude());
        assertEquals(31.53963f, item.getLocation().getLongitude());
        assertNull(item.getHectar());
        assertEquals("Довж. гризла - 103. D кільця - 47", item.getDimensions());
        assertNull(item.getWeight());
        assertTrue(item.getRemarks().contains("Вільний пошук"));
        assertTrue(item.getRemarks().contains("Галявина північніше 4-х сосен через дорогу"));
        assertTrue(item.getRemarks().contains("559"));
        assertNull(item.getCategory());
        assertFalse(item.isNumberExists());
        assertFalse(item.isAutoDetectedCategory());
        assertFalse(item.isAutoDetectedCaliber());
        assertFalse(item.isAutoDetectedDeformed());
        assertFalse(item.isAutoDetectedHectar());
        assertTrue(item.isSave());
    }

    @Test
    public void testParseExistingNumber() throws IOException {
        when(itemRepository.findByPointNumber(any())).thenReturn(Optional.of(new Item()));

        InputStream in = getClass().getResourceAsStream("itemList.csv");
        List<ItemParsingDto> items = parser.parseCsv(in);

        assertEquals(4, items.size());

        assertTrue(items.stream().noneMatch(ItemParsingDto::isSave));
        assertTrue(items.stream().allMatch(ItemParsingDto::isNumberExists));
    }



    @Test
    public void testAutodetectCategory() {
        when(categoryRepository.findAll()).thenReturn(List.of(
                new Category("Swords", List.of("sword", "dagger")),
                new Category("Firearms", List.of("gun", "pistol"))
        ));

        Category sword = parser.autodetectCategory("Sword of Miracles");
        Category firearm = parser.autodetectCategory("Machine gun");
        Category unknown = parser.autodetectCategory("Something weird");

        assertEquals("Swords", sword.getName());
        assertEquals("Firearms", firearm.getName());
        assertNull(unknown);
    }

    @Test
    public void testAutodetectCaliber() {
        assertEquals(16, parser.autodetectCaliber("16x15x12"));
        assertEquals(15, parser.autodetectCaliber("13x15x14"));
        assertEquals(17, parser.autodetectCaliber("17x17x15"));
        assertEquals(14, parser.autodetectCaliber("14x13"));
        assertEquals(11, parser.autodetectCaliber("11x10x18"));
        assertEquals(-1, parser.autodetectCaliber("malformed"));
    }

    @Test
    public void testAutodetectDeformed() {
        assertTrue(parser.autodetectDeformed("Куля деформована"));
        assertTrue(parser.autodetectDeformed("Куля свинцева розплющена"));
        assertTrue(parser.autodetectDeformed("Куля свинцева з хвостиком. деформ."));
        assertFalse(parser.autodetectDeformed("Куля свинцева кругла з зал. хвостика"));
        assertTrue(parser.autodetectDeformed("Куля св кругла з площ деформ"));
        assertFalse(parser.autodetectDeformed("Куля свинцева кругла"));
        assertTrue(parser.autodetectDeformed("Куля св кругла з хвостом розрізана"));
        assertFalse(parser.autodetectDeformed("Куля св напівсферична"));
        assertTrue(parser.autodetectDeformed("Куля св кругла деформована?"));
    }

    @Test
    public void testAutodetectHectar() {
        assertEquals(2, parser.autodetectHectar("Га 2 - исслед 45х82 м юг-вос части"));
        assertEquals(1, parser.autodetectHectar("Га 1 - исследовано 100х82 м"));
        assertEquals(-1, parser.autodetectHectar("ПІВДЕНЬ ГАЙДУК"));
    }
}
