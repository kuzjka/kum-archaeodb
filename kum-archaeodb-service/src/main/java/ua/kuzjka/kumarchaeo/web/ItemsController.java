package ua.kuzjka.kumarchaeo.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.model.Category;
import ua.kuzjka.kumarchaeo.service.ItemsService;

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
    public long addCategory(@RequestBody CategoryDto dto) {
        return itemsService.saveCategory(dto);
    }

    @PutMapping ("/categories")
    public void updateCategory(@RequestBody CategoryDto dto) {
        itemsService.saveCategory(dto);
    }
}
