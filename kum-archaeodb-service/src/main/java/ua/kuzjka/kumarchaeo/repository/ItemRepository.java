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
    List<Item> findAllByCategoryId(int id);
    /**
     * Gets a page of items filtered by categories.
     * @param pageable      Pagination information
     * @param categories    List of category names
     * @return              A page of items filtered by categories.
     */
    Page<Item> findAllByCategoryNameIn(Pageable pageable, List<String> categories);

    /**
     * Finds all items filtered by item IDs.
     * @param ids   List of item IDs
     * @return      List of items where ID matches one of the given IDs
     */
    List<Item> findAllByIdIn(List<Integer> ids);

    /**
     * Finds all items filtered by categories.
     * @param categories    List of category names
     * @return              List of items where category matches one of the given categories
     */
    List<Item> findAllByCategoryNameIn(List<String> categories);

    /**
     * Finds all items filtered by item IDs and categories.
     * @param ids           List of item IDs
     * @param categories    List of category names
     * @return              List of items where ID matches one of the given IDs
     *                      and category matches one of the given categories
     */
    List<Item> findAllByIdInAndCategoryNameIn(List<Integer> ids, List<String> categories);
}
