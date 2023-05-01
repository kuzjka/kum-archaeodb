package ua.kuzjka.kumarchaeo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ua.kuzjka.kumarchaeo.dto.CategoryDto;
import ua.kuzjka.kumarchaeo.service.ItemsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemsController.class)
public class ItemsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemsService itemsService;

    @Test
    void getCategoriesTest() throws Exception{
        String content = "[{\"id\":1,\"name\":\"Кулі\", \"filters\":['кулі']}]";
        List<String> filters = new ArrayList<>();
        List<CategoryDto> categories = new ArrayList<>();
        filters.add("кулі");
        CategoryDto dto = new CategoryDto(1, "Кулі", filters);
        categories.add(dto);

            given(this.itemsService.getCategories()).willReturn(categories);
        this.mvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(content));
        ;

    }
}
