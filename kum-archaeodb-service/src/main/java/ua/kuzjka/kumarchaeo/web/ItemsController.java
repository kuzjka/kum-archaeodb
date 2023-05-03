package ua.kuzjka.kumarchaeo.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.service.ItemsService;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ItemsController {
    private ItemsService itemsService;

    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
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
