package ua.kuzjka.kumarchaeo.parsing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.kuzjka.kumarchaeo.dto.ItemParsingDto;
import ua.kuzjka.kumarchaeo.model.*;
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
        List<ItemParsingDto> items = parser.parseCsv(in, '\t', true);

        assertEquals(6, items.size());
        ItemParsingDto item;

        item = items.get(0);
        assertEquals(new PointNumber(1), item.getNumber());
        assertEquals("Куля свинцева з хвостиком", item.getName());
        assertEquals(33.907521f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(56.718841f, item.getLocation().getLongitude(), 0.000001f);
        assertNull(item.getHectare());
        assertFalse(item.isHectareAutodetected());
        assertEquals("17х17х20", item.getDimensions());
        assertEquals(26.0f, item.getWeight(), 0.1f);
        assertThat(item.getRemarks(), containsString("Маршрут 1"));
        assertEquals("591", item.getGpsPoint());
        assertEquals(BULLETS_NAME, item.getCategory());
        assertTrue(item.isCategoryAutodetected());
        assertEquals(17, item.getCaliber());
        assertFalse(item.isCaliberApproximate());
        assertFalse(item.isCaliberAutodetected());
        assertEquals(Deformation.NONE, item.getDeformation());
        assertFalse(item.isDeformationAutodetected());
        assertFalse(item.isNumberExists());
        assertFalse(item.isHectareAutodetected());
        assertTrue(item.isSave());

        item = items.get(1);
        assertEquals(new PointNumber(16), item.getNumber());
        assertEquals("Наконечник стріли залізний 4-гранний", item.getName());
        assertEquals(47.878316f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(145.40555f, item.getLocation().getLongitude(), 0.000001f);
        assertNull(item.getHectare());
        assertFalse(item.isHectareAutodetected());
        assertEquals("Довж. заг. - 56; довж. пера - 35. товщ. - 8", item.getDimensions());
        assertNull(item.getWeight());
        assertThat(item.getRemarks(), containsString("Маршрут 2"));
        assertThat(item.getRemarks(), containsString("рест"));
        assertEquals("789", item.getGpsPoint());
        assertNull(item.getCategory());
        assertFalse(item.isCategoryAutodetected());
        assertFalse(item.isCaliberAutodetected());
        assertFalse(item.isDeformationAutodetected());
        assertFalse(item.isNumberExists());
        assertTrue(item.isSave());

        item = items.get(2);
        assertEquals(new PointNumber(177), item.getNumber());
        assertEquals("Куля св кругла з зал хвостика деформ", item.getName());
        assertEquals(20.442709f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(31.904602f, item.getLocation().getLongitude(), 0.000001f);
        assertNull(item.getHectare());
        assertFalse(item.isHectareAutodetected());
        assertEquals("18х14х19", item.getDimensions());
        assertEquals(27f, item.getWeight(), 0.1f);
        assertThat(item.getRemarks(), containsString("Південний сектор"));
        assertEquals("623", item.getGpsPoint());
        assertEquals(BULLETS_NAME, item.getCategory());
        assertEquals(17, item.getCaliber());
        assertTrue(item.isCaliberApproximate());
        assertFalse(item.isCaliberAutodetected());
        assertEquals(Deformation.LIGHT, item.getDeformation());
        assertTrue(item.isDeformationAutodetected());
        assertFalse(item.isNumberExists());
        assertTrue(item.isSave());

        item = items.get(3);
        assertEquals(new PointNumber(268), item.getNumber());
        assertEquals("Куля св з зал хвоста розплющена", item.getName());
        assertEquals(7.048307f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(81.145097f, item.getLocation().getLongitude(), 0.000001f);
        assertNull(item.getHectare());
        assertFalse(item.isHectareAutodetected());
        assertEquals("22х17х6", item.getDimensions());
        assertEquals(11, item.getWeight(), 0.1f);
        assertThat(item.getRemarks(), containsString("Трикутний ліс"));
        assertEquals("705", item.getGpsPoint());
        assertEquals(BULLETS_NAME, item.getCategory());
        assertTrue(item.isCategoryAutodetected());
        assertNull(item.getCaliber());
        assertFalse(item.isCaliberAutodetected());
        assertEquals(Deformation.HARD, item.getDeformation());
        assertTrue(item.isDeformationAutodetected());
        assertFalse(item.isNumberExists());
        assertTrue(item.isSave());

        item = items.get(4);
        assertEquals(new PointNumber(512), item.getNumber());
        assertEquals("Куля деформ", item.getName());
        assertEquals(70.293511f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(149.31290f, item.getLocation().getLongitude(), 0.000001f);
        assertEquals(6, item.getHectare());
        assertTrue(item.isHectareAutodetected());
        assertEquals("17х15х14", item.getDimensions());
        assertEquals(18.2f, item.getWeight(), 0.1f);
        assertThat(item.getRemarks(), containsString("Га 6. 01.07.22"));
        assertEquals("786", item.getGpsPoint());
        assertEquals(BULLETS_NAME, item.getCategory());
        assertEquals(16, item.getCaliber());
        assertFalse(item.isCaliberApproximate());
        assertFalse(item.isCaliberAutodetected());
        assertEquals(Deformation.LIGHT, item.getDeformation());
        assertTrue(item.isDeformationAutodetected());
        assertFalse(item.isNumberExists());
        assertTrue(item.isSave());

        item = items.get(5);
        assertEquals(new PointNumber(615, 1), item.getNumber());
        assertEquals("Монета-солід", item.getName());
        assertEquals(17.071103f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(17.971902f, item.getLocation().getLongitude(), 0.000001f);
        assertEquals(2, item.getHectare());
        assertTrue(item.isHectareAutodetected());
        assertEquals("", item.getDimensions());
        assertNull(item.getWeight());
        assertThat(item.getRemarks(), containsString("Га 2 - исслед 45х82 м юг-вос части"));
        assertThat(item.getRemarks(), containsString("Експерементальний гектар"));
        assertEquals("21п№12", item.getGpsPoint());
        assertFalse(item.isCategoryAutodetected());
        assertTrue(item.isSave());
    }

    @Test
    public void testCustomColumnSeparator() throws IOException {
        String content = "268;Куля св з зал хвоста розплющена;22х17х6;11;;;7,048307;81,145097;705;;Трикутний ліс";

        when(itemRepository.findByPointNumber(any())).thenReturn(Optional.empty());

        List<ItemParsingDto> items = parser.parseCsv(content, ';', true);

        assertEquals(1, items.size());

        ItemParsingDto item = items.get(0);

        assertEquals(268, item.getNumber().getNumber());
        assertEquals("Куля св з зал хвоста розплющена", item.getName());
        assertEquals("22х17х6", item.getDimensions());
        assertEquals(11, item.getWeight(), 0.1f);
        assertEquals(7.048307f, item.getLocation().getLatitude(), 0.000001f);
        assertEquals(81.145097f, item.getLocation().getLongitude(), 0.000001f);
        assertEquals("705", item.getGpsPoint());
    }

    @Test
    public void testParseExistingNumber() throws IOException {
        when(itemRepository.findByPointNumber(any())).thenReturn(Optional.of(new Item()));

        InputStream in = getClass().getResourceAsStream("itemList.csv");
        List<ItemParsingDto> items = parser.parseCsv(in, '\t', true);

        assertEquals(6, items.size());

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
        assertEquals(Deformation.LIGHT, parser.autodetectDeformed("Куля деформована"));
        assertEquals(Deformation.HARD, parser.autodetectDeformed("Куля свинцева розплющена"));
        assertEquals(Deformation.LIGHT, parser.autodetectDeformed("Куля свинцева з хвостиком. деформ."));
        assertEquals(Deformation.NONE, parser.autodetectDeformed("Куля свинцева кругла з зал. хвостика"));
        assertEquals(Deformation.LIGHT, parser.autodetectDeformed("Куля св кругла з площ деформ"));
        assertEquals(Deformation.NONE, parser.autodetectDeformed("Куля свинцева кругла"));
        assertEquals(Deformation.HARD, parser.autodetectDeformed("Куля св кругла з хвостом розрізана"));
        assertEquals(Deformation.NONE, parser.autodetectDeformed("Куля св напівсферична"));
        assertEquals(Deformation.LIGHT, parser.autodetectDeformed("Куля св кругла деформована?"));
        assertEquals(Deformation.HARD, parser.autodetectDeformed("Куля св кругла розплющена"));
    }

    @Test
    public void testAutodetectHectar() {
        assertEquals(2, parser.autodetectHectar("Га 2 - исслед 45х82 м юг-вос части"));
        assertEquals(1, parser.autodetectHectar("Га 1 - исследовано 100х82 м"));
        assertEquals(-1, parser.autodetectHectar("ПІВДЕНЬ ГАЙДУК"));
    }
}
