package ua.kuzjka.kumarchaeo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.dto.ItemDto;
import ua.kuzjka.kumarchaeo.dto.ItemParsingDto;
import ua.kuzjka.kumarchaeo.dto.PageDto;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.parsing.ItemListParser;
import ua.kuzjka.kumarchaeo.repository.CategoryRepository;
import ua.kuzjka.kumarchaeo.repository.ItemRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemsService {
    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    private ItemListParser itemListParser;

    public ItemsService(@Autowired ItemRepository itemRepository,
                        @Autowired CategoryRepository categoryRepository,
                        @Autowired ItemListParser itemListParser) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.itemListParser = itemListParser;
    }


    public List<ItemParsingDto> parse(String data, boolean decimal) throws IOException {
        return this.itemListParser.parseCsv(data, decimal);
    }


    public void confirmParsed(List<ItemParsingDto> dtoList) {

        for (ItemParsingDto dto : dtoList) {
            Item item = new Item();
            item.setName(dto.getName());
            item.setPointNumber(dto.getNumber());
            Category category = categoryRepository.findByName(dto.getCategory()).get();
            item.setCategory(category);
            item.setLocation(dto.getLocation());
            item.setDimensions(dto.getDimensions());
            item.setHectare(dto.getHectare());
            itemRepository.save(item);
        }
    }

    /**
     * returns page dto with list of ItemDto
     *
     * @param page
     * @param size
     * @param categories
     * @param sort
     * @param order
     * @return page dto
     */
    public PageDto getItems(int page, int size, String categories, String sort, String order) {
        List<ItemDto> items;
        long totalCount;
        long totalPages;
        items = itemRepository.findAll(PageRequest.of(page, size)).stream().map(ItemDto::new).collect(Collectors.toList());
        totalCount = itemRepository.count();
        totalPages = itemRepository.findAll(PageRequest.of(0, size)).getTotalPages();
        PageDto dto = new PageDto();
        dto.setContent(items);
        dto.setTotalCount(totalCount);
        dto.setTotalPages(totalPages);
        return dto;

    }

    /**
     * Gets all categories.
     *
     * @return List of categories
     */
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().
                stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    /**
     * Gets all category names
     *
     * @return List of category names
     */
    public List<String> getCategoryNames() {
        List<String> names = new ArrayList<>();
        categoryRepository.findAll().forEach(x -> names.add(x.getName()));
        return names;
    }

    /**
     * Gets category by ID.
     *
     * @param id Category ID
     * @return Category for given ID
     * @throws java.util.NoSuchElementException if there is no category for the ID
     */
    public CategoryDto getCategory(int id) {
        Category category = categoryRepository.findById(id).get();
        return new CategoryDto(category);
    }

    /**
     * Saves existing category or adds new.
     *
     * @param categoryDto Category to add/update
     * @return id of category
     * @throws java.util.NoSuchElementException when trying to update non-existing category
     */
    public int saveCategory(CategoryDto categoryDto) {
        Category category;
        if (categoryDto.getId() == null) {
            category = new Category();
        } else {

            Optional<Category> optional = categoryRepository.findById(categoryDto.getId());
            if (optional.isEmpty()) {
                return -1;
            } else {
                category = optional.get();
            }

        }
        category.setName(categoryDto.getName());
        category.setFilters(categoryDto.getFilters());
        return categoryRepository.save(category).getId();
    }


    /**
     * Deletes a category.
     *
     * @param id Category id
     * @throws java.util.NoSuchElementException if trying to delete non-existing category
     */


    public int deleteCategory(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return -1;
        }
        categoryRepository.delete(category.get());
        return id;
    }

    /**
     * Gets info on a single item.
     *
     * @param id Item ID
     * @return Item information
     * @throws java.util.NoSuchElementException if no item exist for this ID
     */
    public ItemDto getItem(int id) {
        Item item = itemRepository.findById(id).get();
        return new ItemDto(item);
    }
}
