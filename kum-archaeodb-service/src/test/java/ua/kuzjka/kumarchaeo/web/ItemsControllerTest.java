package ua.kuzjka.kumarchaeo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.service.ItemsService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ItemsController.class)
public class ItemsControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ItemsService itemsService;

    @Test
    void getCategoriesTest() throws Exception {

        List<String> filters1 = new ArrayList<>();
        List<String> filters2 = new ArrayList<>();
        List<CategoryDto> categories = new ArrayList<>();
        filters1.add("Куля");
        filters2.add("Мушкет");
        CategoryDto dto1 = new CategoryDto(1, "Кулі", filters1);
        CategoryDto dto2 = new CategoryDto(2, "Вогнепальна зброя", filters2);
        categories.add(dto1);
        categories.add(dto2);
        given(this.itemsService.getCategories()).willReturn(categories);
        this.mvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Кулі")))
                .andExpect(jsonPath("$[0].filters[0]", is("Куля")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Вогнепальна зброя")))
                .andExpect(jsonPath("$[1].filters[0]", is("Мушкет")));
    }

    @Test
    void getCategoryNamesTest() throws Exception {

        List<String> names = new ArrayList<>();
        names.add("Вогнепальна зброя");
        names.add("Кулі");
        names.add("Спорядження вершника");
        given(this.itemsService.getCategoryNames()).willReturn(names);
        this.mvc.perform(get("/api/categoryNames")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("Вогнепальна зброя")))
                .andExpect(jsonPath("$[1]", is("Кулі")))
                .andExpect(jsonPath("$[2]", is("Спорядження вершника")));
    }

    @Test
    void addCategoryTest() throws Exception {
        String json = "{\"id\":null, \"name\":\"Обладунки\", \"filters\":[\"Обладунок\"]}";
        List<String> filters = new ArrayList<>();
        filters.add("Обладунок");
        CategoryDto dto = new CategoryDto(null, "Обладунки", filters);
        given(this.itemsService.saveCategory(dto)).willReturn(3);

        this.mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(3)));

    }

    @Test
    void updateCategoryTest() throws Exception {
        String json = "{\"id\":1, \"name\":\"Обладунки\", \"filters\":[\"Обладунок\"]}";
        List<String> filters = new ArrayList<>();
        filters.add("Обладунок");
        CategoryDto dto = new CategoryDto(1, "Обладунки", filters);
        given(this.itemsService.saveCategory(dto)).willReturn(1);
        this.mvc.perform(put("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updateNotFoundCategoryTest() throws Exception {
        String json = "{\"id\":4, \"name\":\"Обладунки\", \"filters\":[\"Обладунок\"]}";
        List<String> filters = new ArrayList<>();
        filters.add("Обладунок");
        CategoryDto dto = new CategoryDto(4, "Обладунки", filters);
        given(this.itemsService.saveCategory(dto)).willReturn(-1);
        this.mvc.perform(put("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCategoryTest() throws Exception {
        given(this.itemsService.deleteCategory(1)).willReturn(1);
        this.mvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk());
    }
    @Test
    void deleteNotFoundCategoryTest() throws Exception {
        given(this.itemsService.deleteCategory(4)).willReturn(-1);
        this.mvc.perform(delete("/api/categories/4"))
                .andExpect(status().is(404));
    }
}
