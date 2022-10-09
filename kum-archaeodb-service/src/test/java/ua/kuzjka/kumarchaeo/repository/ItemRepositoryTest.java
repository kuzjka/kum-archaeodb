package ua.kuzjka.kumarchaeo.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.kuzjka.kumarchaeo.model.*;

import java.util.Optional;

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
        item.setPointNumber(new PointNumber(111));
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
        assertEquals(new PointNumber(111), item.getPointNumber());
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
        item.setPointNumber(new PointNumber(123, 1));
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

    @Test
    public void testBulletAdded() {
        Category bulletCategory = new Category();
        bulletCategory.setName("Bullets");
        categoryRepository.save(bulletCategory);

        Bullet bullet = new Bullet();
        bullet.setName("New Bullet");
        bullet.setPointNumber(new PointNumber(124, 2));
        bullet.setCaliber(14);
        bullet.setCategory(bulletCategory);
        bullet.setLocation(new Location(3.0f, 3.0f));

        repository.save(bullet);

        assertEquals(2, repository.count());

        Optional<Item> item = repository.findByPointNumber(new PointNumber(124, 2));

        assertTrue(item.isPresent());
        assertEquals("New Bullet", item.get().getName());
        assertTrue(item.get() instanceof Bullet);

        assertEquals(14, ((Bullet) item.get()).getCaliber());
    }

    @Test
    public void testGetByNumber() {
        Optional<Item> item = repository.findByPointNumber(new PointNumber(111));

        assertTrue(item.isPresent());
        assertEquals("Existing Item", item.get().getName());
    }

    @Test
    public void testGetByNumberDoesntExist() {
        Optional<Item> item = repository.findByPointNumber(new PointNumber(100500, 14));

        assertFalse(item.isPresent());
    }
}
