package ua.kuzjka.kumarchaeo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ua.kuzjka.kumarchaeo.model.*;

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

    @Value("${archaeodb.bulletsCategory}")
    private String bulletsCategory;

    @BeforeEach
    public void setUp() {
        Category category = categoryRepository.findByName(bulletsCategory).orElseThrow();

        Location location = new Location(1.0f, 1.0f);

        Bullet bullet = new Bullet();
        bullet.setPointNumber(new PointNumber(111));
        bullet.setName("Bullet");
        bullet.setCategory(category);
        bullet.setLocation(location);
        bullet.setCaliber(14);
        bullet.setApproximate(true);
        bullet.setStandard(BulletStandard.COSSACK);
        bullet.setDeformed(Deformation.LIGHT);
        bullet.setImprints(true);
        repository.save(bullet);
    }

    @Test
    public void testGetBullet() {
        assertEquals(1, repository.count());

        Bullet bullet = repository.findAll().get(0);
        assertEquals("Bullet", bullet.getName());
        assertEquals(new PointNumber(111), bullet.getPointNumber());
        assertEquals(bulletsCategory, bullet.getCategory().getName());
        assertEquals(1.0f, bullet.getLocation().getLongitude());
        assertEquals(1.0f, bullet.getLocation().getLatitude());
        assertEquals(14, bullet.getCaliber());
        assertTrue(bullet.isApproximate());
        assertEquals(BulletStandard.COSSACK, bullet.getStandard());
        assertEquals(Deformation.LIGHT, bullet.getDeformed());
        assertTrue(bullet.hasImprints());
    }

    @Test
    public void testGetBulletAsItem() {
       assertEquals(1, itemRepository.count());

       Item item = itemRepository.findAll().get(0);
       assertEquals("Bullet", item.getName());
       assertEquals(bulletsCategory, item.getCategory().getName());
    }

    @Test
    public void testBulletAdded() {
        assertEquals(1, categoryRepository.count());
        Category category = categoryRepository.findAll().get(0);

        Bullet newBullet = new Bullet();
        newBullet.setPointNumber(new PointNumber(123));
        newBullet.setName("New Bullet");
        newBullet.setCaliber(17);
        newBullet.setDeformed(Deformation.NONE);
        newBullet.setCategory(category);
        newBullet.setLocation(new Location(2.0f, 2.0f));
        repository.save(newBullet);

        assertEquals(2, repository.count());
        Optional<Bullet> bullet = repository.findByPointNumber(new PointNumber(123));
        assertTrue(bullet.isPresent());
        assertEquals("New Bullet", bullet.get().getName());
        assertEquals(17, bullet.get().getCaliber());
        assertEquals(Deformation.NONE, bullet.get().getDeformed());
    }

    @Test
    public void testBulletRemoved() {
        assertEquals(1, repository.count());

        Bullet bullet = repository.findAll().get(0);
        repository.delete(bullet);

        assertEquals(0, repository.count());
    }
}
