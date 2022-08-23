package ua.kuzjka.kumarchaeo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.kuzjka.kumarchaeo.model.Bullet;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.Location;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BulletRepositoryTest {
    @Autowired
    private BulletRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        Category category = new Category();
        category.setName("Bullets");
        categoryRepository.save(category);

        Location location = new Location(1.0f, 1.0f);

        Bullet bullet = new Bullet();
        bullet.setName("Bullet");
        bullet.setCategory(category);
        bullet.setLocation(location);
        bullet.setCaliber(14);
        bullet.setDeformed(true);
        repository.save(bullet);
    }

    @Test
    public void testGetBullet() {
        assertEquals(1, repository.count());

        Bullet bullet = repository.findAll().get(0);
        assertEquals("Bullet", bullet.getName());
        assertEquals("Bullets", bullet.getCategory().getName());
        assertEquals(1.0f, bullet.getLocation().getLongitude());
        assertEquals(1.0f, bullet.getLocation().getLatitude());
        assertEquals(14, bullet.getCaliber());
        assertTrue(bullet.isDeformed());
    }

    @Test
    public void testGetBulletAsItem() {
       assertEquals(1, itemRepository.count());

       Item item = itemRepository.findAll().get(0);
       assertEquals("Bullet", item.getName());
       assertEquals("Bullets", item.getCategory().getName());
    }

    @Test
    public void testBulletAdded() {
        assertEquals(1, categoryRepository.count());
        Category category = categoryRepository.findAll().get(0);

        Bullet newBullet = new Bullet();
        newBullet.setName("New Bullet");
        newBullet.setCaliber(17);
        newBullet.setDeformed(false);
        newBullet.setCategory(category);
        newBullet.setLocation(new Location(2.0f, 2.0f));
        repository.save(newBullet);

        assertEquals(2, repository.count());
        Optional<Bullet> bullet = repository.findAll().stream()
                .filter(b -> "New Bullet".equals(b.getName()))
                .findAny();
        assertTrue(bullet.isPresent());
        assertEquals(17, bullet.get().getCaliber());
        assertFalse(bullet.get().isDeformed());
    }

    @Test
    public void testBulletRemoved() {
        assertEquals(1, repository.count());

        Bullet bullet = repository.findAll().get(0);
        repository.delete(bullet);

        assertEquals(0, repository.count());
    }
}
