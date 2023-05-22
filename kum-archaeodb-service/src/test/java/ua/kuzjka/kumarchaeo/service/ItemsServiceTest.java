package ua.kuzjka.kumarchaeo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.dto.ItemDto;
import ua.kuzjka.kumarchaeo.exception.NoSuchCategoryException;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.Location;
import ua.kuzjka.kumarchaeo.model.PointNumber;
import ua.kuzjka.kumarchaeo.parsing.ItemListParser;
import ua.kuzjka.kumarchaeo.repository.CategoryRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ItemsServiceTest {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    private ItemsService service;
    private ItemListParser itemListParser;

    @BeforeEach
    public void setUp() {
        itemRepository = mock(ItemRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        itemListParser = mock(ItemListParser.class);
        service = new ItemsService(itemRepository, categoryRepository, itemListParser);
    }

    @Test
    public void testGetCategories() {
        Category cat1 = new Category("Category1", List.of("Cat1 filter"));
        cat1.setId(1);
        Category cat2 = new Category("Category2", List.of("Cat2 filter1", "Cat2 filter2"));
        cat2.setId(2);

        when(categoryRepository.findAll()).thenReturn(List.of(cat1, cat2));

        List<CategoryDto> categories = service.getCategories();
        assertEquals(2, categories.size());

        assertThat(categories, hasItem(new CategoryDto(1, "Category1", List.of("Cat1 filter"))));
        assertThat(categories, hasItem(new CategoryDto(2, "Category2", List.of("Cat2 filter1", "Cat2 filter2"))));
    }

    @Test
    public void testGetCategory() {
        Category cat = new Category("Category Name", List.of("Filter1", "Filter2"));
        cat.setId(123);

        when(categoryRepository.findById(123)).thenReturn(Optional.of(cat));

        CategoryDto dto = service.getCategory(123);
        assertEquals(new CategoryDto(123, "Category Name", List.of("Filter1", "Filter2")), dto);
    }

    @Test
    public void testCategoryNotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getCategory(587));
    }

    @Test
    public void testAddNewCategory() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(categoryRepository.save(ArgumentMatchers.any(Category.class)))
                .then((Answer<? extends Category>) invocation -> {
                    Category cat = invocation.getArgument(0, Category.class);
                    cat.setId(123);
                    return cat;
                });

        CategoryDto dto = new CategoryDto(null, "New Category", List.of("Filter1", "Filter2"));
        int id = service.saveCategory(dto);

        assertEquals(123, id);
        verify(categoryRepository).save(argThat(cat ->
                cat.getName().equals("New Category") &&
                        cat.getFilters().containsAll(List.of("Filter1", "Filter2"))));
    }

    @Test
    public void testUpdateExistingCategory() {
        Category cat = new Category("Old name", List.of("Old filter"));
        cat.setId(234);

        when(categoryRepository.findById(234)).thenReturn(Optional.of(cat));
        when(categoryRepository.save(ArgumentMatchers.any(Category.class)))
                .then(invocation -> invocation.getArgument(0, Category.class));

        CategoryDto dto = new CategoryDto(234, "New Name", List.of("New Filter1", "New Filter2"));
        service.saveCategory(dto);

        verify(categoryRepository).save(argThat(c ->
                c.getId() == 234 &&
                        "New Name".equals(c.getName()) &&
                        c.getFilters().containsAll(List.of("New Filter1", "New Filter2"))));
    }

    @Test
    public void testUpdateNonExistingCategory() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        CategoryDto dto = new CategoryDto(235, "New Name", List.of("Filter"));
        assertThrows(NoSuchCategoryException.class, () -> service.saveCategory(dto));
    }

    @Test
    public void testDeleteExistingCategory() {
        Category cat = new Category("Category", Collections.emptyList());
        cat.setId(236);

        when(categoryRepository.findById(236)).thenReturn(Optional.of(cat));

        service.deleteCategory(236);

        verify(categoryRepository).delete(argThat(c -> c.getId() == 236 && c.getName().equals("Category")));
    }

    @Test
    public void testDeleteNonExistingCategory() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertEquals(-1, service.deleteCategory(237));
    }

    @Test
    public void testGetExistingItem() {
        Category category = new Category("Category Name", Collections.emptyList());

        Item item = new Item();
        item.setId(125);
        item.setName("Item Name");
        item.setYear(2022);
        item.setPointNumber(new PointNumber(14, 1));
        item.setHectare(5);
        item.setDepth(25);
        item.setLocation(new Location(1.2f, 3.4f));
        item.setDescription("Item description");
        item.setMaterial("Steel");
        item.setTechnique("Die cast");
        item.setCondition("Oxidized");
        item.setDimensions("15x14x12");
        item.setWeight(23f);
        item.setCategory(category);
        item.setRemarks("Item remarks");
        item.setImage("00125.jpg");

        when(itemRepository.findById(125)).thenReturn(Optional.of(item));

        ItemDto dto = service.getItem(125);

        assertEquals(125, dto.getId());
        assertEquals("Item Name", dto.getName());
        assertEquals(2022, dto.getYear());
        assertEquals("14/1", dto.getPointNumber());
        assertEquals(5, dto.getHectare());
        assertEquals(25, dto.getDepth());
        assertEquals(1.2f, dto.getLocation().getLatitude());
        assertEquals(3.4f, dto.getLocation().getLongitude());
        assertEquals("Item description", dto.getDescription());
        assertEquals("Steel", item.getMaterial());
        assertEquals("Die cast", item.getTechnique());
        assertEquals("Oxidized", dto.getCondition());
        assertEquals("15x14x12", dto.getDimensions());
        assertEquals(23f, dto.getWeight());
        assertEquals("Category Name", dto.getCategory());
        assertEquals("Item remarks", dto.getRemarks());
        assertEquals("00125.jpg", dto.getImageFile());
    }

    @Test
    public void testGetNonExistingItem() {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getItem(124));
    }
}
