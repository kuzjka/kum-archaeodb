package ua.kuzjka.kumarchaeo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kuzjka.kumarchaeo.model.Bullet;

@Repository
public interface BulletRepository extends JpaRepository<Bullet, Integer> {
}
