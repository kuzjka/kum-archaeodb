package ua.kuzjka.kumarchaeo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kuzjka.kumarchaeo.model.Item;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    /**
     * Finds item by number.
     * @return  Optional item
     */
    Optional<Item> findByNumber(String number);
}
