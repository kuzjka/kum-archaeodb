package ua.kuzjka.kumarchaeo.web;


import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kuzjka.kumarchaeo.dto.*;
import ua.kuzjka.kumarchaeo.service.ItemsService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ItemsController {
    private ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/items")
    public PageDto getItems(@RequestParam int page,
                            @RequestParam int size,

                            @RequestParam String sort,
                            @RequestParam String order) {
        return itemsService.getItems(page, size, sort, order);
    }

    @GetMapping("/categoryNames")
    public List<String> getCategoryNames() {
        return itemsService.getCategoryNames();
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return itemsService.getCategories();
    }

    @PostMapping("/categories")
    public int addCategory(@RequestBody CategoryDto dto) {
        return itemsService.saveCategory(dto);
    }

    @PostMapping("/items/parse")
    public List<ItemParsingDto> parse(@RequestBody ItemParsingRequestDto dto) throws IOException {
        return itemsService.parse(dto);
    }

    @PostMapping("/items/addParsed")
    public void addParsed(@RequestBody List<ItemParsingDto> dtoList) throws IOException {
        itemsService.confirmParsed(dtoList);
    }

    @PutMapping("/categories")
    public ResponseEntity updateCategory(@RequestBody CategoryDto dto) {
        int code = itemsService.saveCategory(dto);
        if (code == -1) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity deleteCategory(@PathVariable int id) {
        int code = itemsService.deleteCategory(id);
        if (code == -1) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}
