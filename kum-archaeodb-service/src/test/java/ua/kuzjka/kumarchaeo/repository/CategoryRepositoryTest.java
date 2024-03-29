package ua.kuzjka.kumarchaeo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.kuzjka.kumarchaeo.model.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;

    @BeforeEach
    public void setUp() {
        Category category = new Category();
        category.setName("Existing Category");
        category.setFilters(List.of("Filter1", "Filter2"));
        repository.save(category);
    }

    @Test
    public void testGetCategory() {
        assertTrue(repository.count() >= 1);

        Optional<Category> category = repository.findByName("Existing Category");

        assertTrue(category.isPresent());
        assertEquals("Existing Category", category.get().getName());
        assertTrue(category.get().getFilters().contains("Filter1"));
        assertTrue(category.get().getFilters().contains("Filter2"));
    }

    @Test
    public void testCategoryAdded() {
        Category newCategory = new Category();
        newCategory.setName("New Category");
        newCategory.setFilters(List.of("NewFilter1", "NewFilter2"));
        repository.save(newCategory);

        assertTrue(repository.count() >= 2);
        Optional<Category> category = repository.findAll().stream()
                .filter(c -> "New Category".equals(c.getName()))
                .findFirst();
        assertTrue(category.isPresent());
        assertTrue(category.get().getFilters().contains("NewFilter1"));
        assertTrue(category.get().getFilters().contains("NewFilter2"));
    }

    @Test
    public void testCategoryRemoved() {
        long count = repository.count();

        Category category = repository.findByName("Existing Category").orElseThrow();
        repository.delete(category);

        assertEquals(count - 1, repository.count());
    }

    @Test
    public void testFindByName() {
        Optional<Category> category = repository.findByName("Existing Category");

        assertTrue(category.isPresent());
        assertEquals("Existing Category", category.get().getName());
        assertTrue(category.get().getFilters().contains("Filter1"));
        assertTrue(category.get().getFilters().contains("Filter2"));
    }

    @Test
    public void testFindByNameDoesntExist() {
        Optional<Category> category = repository.findByName("Non-existing name");

        assertFalse(category.isPresent());
    }
}
