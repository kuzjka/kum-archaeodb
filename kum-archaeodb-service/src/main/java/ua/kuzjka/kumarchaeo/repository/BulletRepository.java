package ua.kuzjka.kumarchaeo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kuzjka.kumarchaeo.model.Bullet;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.PointNumber;

import java.util.Optional;

@Repository
public interface BulletRepository extends JpaRepository<Bullet, Integer> {
    /**
     * Finds item by number.
     * @return  Optional item
     */
    Optional<Bullet> findByPointNumber(PointNumber pointNumber);
}
