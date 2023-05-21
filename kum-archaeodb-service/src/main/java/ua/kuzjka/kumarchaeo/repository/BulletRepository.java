package ua.kuzjka.kumarchaeo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kuzjka.kumarchaeo.model.Bullet;
import ua.kuzjka.kumarchaeo.model.PointNumber;

import java.util.List;
import java.util.Optional;

@Repository
public interface BulletRepository extends JpaRepository<Bullet, Integer> {
    /**
     * Finds item by number.
     * @return  Optional item
     */
    Optional<Bullet> findByPointNumber(PointNumber pointNumber);

    /**
     * Finds all bullets filtered by IDs.
     * @param ids   List of item IDs
     * @return      List of bullets where ID matches one of the given IDs
     */
    List<Bullet> findAllByIdIn(List<Integer> ids);
}
