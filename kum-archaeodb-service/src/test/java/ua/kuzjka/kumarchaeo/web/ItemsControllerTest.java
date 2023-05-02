package ua.kuzjka.kumarchaeo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.BDDMockito.given;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;

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

}
