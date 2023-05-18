package ua.kuzjka.kumarchaeo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kuzjka.kumarchaeo.dto.ItemDto;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.PointNumber;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    /**
     * Finds item by number.
     * @return  Optional item
     */
    Optional<Item> findByPointNumber(PointNumber pointNumber);

    Page<Item> findAllByCategoryNameIn(Pageable pageable, List<String> categories);
}
