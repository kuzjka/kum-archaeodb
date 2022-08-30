package ua.kuzjka.kumarchaeo.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.dto.ItemDto;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.repository.CategoryRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ItemsService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    public ItemsService(@Autowired ItemRepository itemRepository, @Autowired CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Gets all categories.
     * @return  List of categories
     */
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().
                stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    /**
     * Gets category by ID.
     * @param id    Category ID
     * @return      Category for given ID
     * @throws java.util.NoSuchElementException if there is no category for the ID
     */
    public CategoryDto getCategory(int id) {
        Category category = categoryRepository.findById(id).get();
        return new CategoryDto(category);
    }

    /**
     * Saves existing category or adds new.
     * @param categoryDto   Category to add/update
     * @throws java.util.NoSuchElementException when trying to update non-existing category
     */
    public void saveCategory(CategoryDto categoryDto) {
        Category category;
        if (categoryDto.getId() == null) {
            category = new Category();
        } else {
            category = categoryRepository.findById(categoryDto.getId()).get();
        }

        category.setName(categoryDto.getName());
        category.setFilters(categoryDto.getFilters());

        categoryRepository.save(category);
    }

    /**
     * Deletes a category.
     * @param id    Category id
     * @throws java.util.NoSuchElementException if trying to delete non-existing category
     */
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).get();
        categoryRepository.delete(category);
    }

    /**
     * Gets info on a single item.
     * @param id    Item ID
     * @return      Item information
     * @throws java.util.NoSuchElementException if no item exist for this ID
     */
    public ItemDto getItem(int id) {
        Item item = itemRepository.findById(id).get();
        return new ItemDto(item);
    }
}