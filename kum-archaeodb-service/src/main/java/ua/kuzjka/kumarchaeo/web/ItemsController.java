package ua.kuzjka.kumarchaeo.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kuzjka.kumarchaeo.dto.*;
import ua.kuzjka.kumarchaeo.export.ItemExportService;
import ua.kuzjka.kumarchaeo.export.ItemsExportException;
import ua.kuzjka.kumarchaeo.service.ItemsService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ItemsController {
    private ItemsService itemsService;
    private ItemExportService itemExportService;

    public ItemsController(ItemsService itemsService, ItemExportService itemExportService) {
        this.itemsService = itemsService;
        this.itemExportService = itemExportService;
    }

    @GetMapping("/items")
    public PageDto getItems(@RequestParam int page,
                            @RequestParam int size,
                            @RequestParam List<String> categories,
                            @RequestParam String sort,
                            @RequestParam String order) {
        return itemsService.getItems(page, size, categories, sort, order);
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
    public int addParsed(@RequestBody List<ItemParsingDto> dtoList) throws IOException {
        return itemsService.confirmParsed(dtoList);
    }

    @PostMapping(value = "/exportItems", produces = "application/octet-stream")
    public byte[] exportItems(@RequestBody ItemExportRequest request) {
        List<Integer> ids = request.getIds();
        if (ids != null && ids.isEmpty()) ids = null;

        List<String> categories = request.getCategories();
        if (categories != null && categories.isEmpty()) categories = null;

        return itemExportService.exportItems(ids, categories);
    }

    @PostMapping(value = "/exportBullets", produces = "application/octet-stream")
    public byte[] exportBullets(@RequestBody BulletExportRequest request) {
        List<Integer> ids = request.getIds();
        if (ids != null && ids.isEmpty()) ids = null;

        return itemExportService.exportBullets(ids);
    }

    @PutMapping("/categories")
    public void updateCategory(@RequestBody CategoryDto dto) {

        itemsService.saveCategory(dto);

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

    @ExceptionHandler(ItemsExportException.class)
    public ResponseEntity<ErrorResponseDto> handleItemExportException(ItemsExportException e) {
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
