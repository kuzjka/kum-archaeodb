package ua.kuzjka.kumarchaeo.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        Category category = new Category();
        category.setName("Existing Category");
        categoryRepository.save(category);

        Location location = new Location(1.0f, 1.0f);

        Item item = new Item();
        item.setName("Existing Item");
        item.setYear(2022);
        item.setNumber(123);
        item.setHectare(2);
        item.setDepth(25);
        item.setLocation(location);
        item.setDescription("Item description");
        item.setMaterial("Steel");
        item.setTechnique("Casting");
        item.setCondition("Oxidized");
        item.setDimensions("25x18x12");
        item.setWeight(35);
        item.setCategory(category);
        item.setRemarks("remarks");
        item.setImage("001.jpg");

        repository.save(item);
    }

    @Test
    public void testGetItem() {
        assertEquals(1, repository.count());
        Item item = repository.findAll().get(0);

        assertEquals("Existing Item", item.getName());
        assertEquals(2022, item.getYear());
        assertEquals(123, item.getNumber());
        assertEquals(2, item.getHectare());
        assertEquals(25, item.getDepth());
        assertEquals(1.0f, item.getLocation().getLatitude());
        assertEquals(1.0f, item.getLocation().getLongitude());
        assertEquals("Item description", item.getDescription());
        assertEquals("Steel", item.getMaterial());
        assertEquals("Casting", item.getTechnique());
        assertEquals("Oxidized", item.getCondition());
        assertEquals("25x18x12", item.getDimensions());
        assertEquals("Existing Category", item.getCategory().getName());
        assertEquals("remarks", item.getRemarks());
        assertEquals("001.jpg", item.getImage());
    }

    @Test
    public void testItemAdded() {
        Category category = categoryRepository.findAll().get(0);

        Item item = new Item();
        item.setName("My item");
        item.setLocation(new Location(1.0f, 1.0f));
        item.setCategory(category);
        repository.save(item);

        assertEquals(2, repository.count());
        assertTrue(repository.findAll().stream().anyMatch(i -> "My item".equals(i.getName())));
    }

    @Test
    public void testItemRemoved() {
        assertEquals(1, repository.count());
        Item item = repository.findAll().get(0);
        repository.delete(item);

        assertEquals(0, repository.count());
    }
}
