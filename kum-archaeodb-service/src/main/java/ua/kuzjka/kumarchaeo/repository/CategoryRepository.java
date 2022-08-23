package ua.kuzjka.kumarchaeo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kuzjka.kumarchaeo.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
