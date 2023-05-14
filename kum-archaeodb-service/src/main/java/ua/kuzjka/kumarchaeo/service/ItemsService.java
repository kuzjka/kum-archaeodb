package ua.kuzjka.kumarchaeo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.kuzjka.kumarchaeo.dto.*;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.model.Delimiter;
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

    /**
     * Parses CSV string and returns list of items to confirm
     *
     * @param dto
     * @param decimal
     * @return list of items
     * @throws IOException
     */
    public List<ItemParsingDto> parse(ItemParsingRequestDto dto, boolean decimal) throws IOException {
        char columnSeparator = '0';
        if (dto.getDelimiter() == Delimiter.TAB) {
            columnSeparator = '\t';
        } else if (dto.getDelimiter() == Delimiter.COMMA) {
            columnSeparator = ',';
        } else if (dto.getDelimiter() == Delimiter.SEMICOLON) {
            columnSeparator = ';';
        }
        return this.itemListParser.parseCsv(dto.getData(), columnSeparator, decimal);
    }

    /**
     * Saves parsed items after confirmation
     *
     * @param dtoList
     */
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
            item.setGpsPoint(dto.getGpsPoint());
            item.setRemarks(dto.getRemarks());
            item.setWeight(dto.getWeight());
            itemRepository.save(item);
        }
    }

    /**
     * Returns page of items
     *
     * @param page
     * @param size
     * @return page of items
     */
    public PageDto getItems(int page, int size) {
        Page<Item> itemPage;
        itemPage = itemRepository.findAll(PageRequest.of(page, size));
        List<ItemDto> items = itemPage.stream().map(ItemDto::new).collect(Collectors.toList());
        PageDto dto = new PageDto();
        dto.setContent(items);
        dto.setTotalCount(itemPage.getTotalElements());
        dto.setTotalPages(itemPage.getTotalPages());
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
     * Saves new category or updates existing.
     *
     * @param categoryDto   Category to add
     * @return              ID of category or {@code -1} if the category is not found
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
     * @param id    Category ID
     * @return      Deleted category ID or {@code -1} if the category was not found
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
