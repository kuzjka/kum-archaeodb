package ua.kuzjka.kumarchaeo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kuzjka.kumarchaeo.dto.*;
import ua.kuzjka.kumarchaeo.exception.NoSuchCategoryException;
import ua.kuzjka.kumarchaeo.exception.SuchCategoryExistsException;
import ua.kuzjka.kumarchaeo.export.ItemExportService;
import ua.kuzjka.kumarchaeo.export.ItemsExportException;
import ua.kuzjka.kumarchaeo.model.Delimiter;
import ua.kuzjka.kumarchaeo.model.Location;
import ua.kuzjka.kumarchaeo.model.PointNumber;
import ua.kuzjka.kumarchaeo.service.ItemsService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ItemsController.class)
public class ItemsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemsService itemsService;

    @MockBean
    private ItemExportService itemExportService;

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
    void getItemsTest() throws Exception {
        List<ItemDto> items = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        categories.add("Кулі");
        PageDto pageDto = new PageDto();
        ItemDto itemDto1 = new ItemDto();
        itemDto1.setId(1);
        itemDto1.setName("Куля свинцева з хвостиком");
        itemDto1.setCategory("Кулі");
        itemDto1.setPointNumber("1");
        itemDto1.setYear(2021);
        itemDto1.setLocation(new Location(11.111f, 22.222f));
        itemDto1.setDimensions("17х17х20");
        ItemDto itemDto2 = new ItemDto();
        itemDto2.setId(2);
        itemDto2.setName("Монета-солід, гаманець №1");
        itemDto2.setCategory("Монети");
        itemDto2.setPointNumber("362/1");
        itemDto2.setYear(2021);
        itemDto2.setLocation(new Location(33.333f, 44.444f));
        items.add(itemDto1);
        items.add(itemDto2);
        pageDto.setContent(items);
        pageDto.setTotalCount(items.size());
        pageDto.setTotalPages(1);
        given(itemsService.getItems(0, 25, categories, "name", "ASC")).willReturn(pageDto);
        this.mvc.perform(get("/api/items")
                        .param("page", "0")
                        .param("size", "25")
                        .param("categories", "Кулі")
                        .param("sort", "name")
                        .param("order", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("Куля свинцева з хвостиком")))
                .andExpect(jsonPath("$.content[0].category", is("Кулі")))
                .andExpect(jsonPath("$.content[0].year", is(2021)))
                .andExpect(jsonPath("$.content[0].pointNumber", is("1")))
                .andExpect(jsonPath("$.content[0].location.latitude", is(11.111)))
                .andExpect(jsonPath("$.content[0].location.longitude", is(22.222)))
                .andExpect(jsonPath("$.content[0].dimensions", is("17х17х20")))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].name", is("Монета-солід, гаманець №1")))
                .andExpect(jsonPath("$.content[1].category", is("Монети")))
                .andExpect(jsonPath("$.content[1].year", is(2021)))
                .andExpect(jsonPath("$.content[1].pointNumber", is("362/1")))
                .andExpect(jsonPath("$.content[1].location.latitude", is(33.333)))
                .andExpect(jsonPath("$.content[1].location.longitude", is(44.444)));

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
    void parseTest() throws Exception {
        ItemParsingRequestDto requestDto = new ItemParsingRequestDto();
        requestDto.setData("6\\tКуля свинцева з хвостиком\\t17х17х19\\t26\\t17\\t\\t49,519675\\t31,538117\\t779\\tМаршрут 1\\t\\t\\n7\\tКуля свинцева деформована\\t17х18х13\\t19\\t\\t\\t49,519684\\t31,538224\\t780\\tМаршрут 1\\t\\t\\n\\t\\n");
        requestDto.setDelimiter(Delimiter.TAB);
        List<ItemParsingDto> items = new ArrayList<>();
        ItemParsingDto item1 = new ItemParsingDto();
        item1.setName("Куля свинцева з хвостиком");
        item1.setNumber(new PointNumber(6, 0));
        item1.setSave(true);
        ItemParsingDto item2 = new ItemParsingDto();
        item2.setName("Куля свинцева деформована");
        item2.setNumber(new PointNumber(7, 0));
        item2.setSave(true);
        items.add(item1);
        items.add(item2);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(requestDto);
        given(this.itemsService.parse(ArgumentMatchers.any(ItemParsingRequestDto.class))).willReturn(items);
        this.mvc.perform(post("/api/items/parse")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Куля свинцева з хвостиком")))
                .andExpect(jsonPath("$[1].name", is("Куля свинцева деформована")));
    }

    @Test
    public void addParsedTest() throws Exception {
        List<ItemParsingDto> items = new ArrayList<>();
        ItemParsingDto item1 = new ItemParsingDto();
        ItemParsingDto item2 = new ItemParsingDto();
        item1.setNumber(new PointNumber(6, 0));
        item1.setName("Куля свинцева з хвостиком");
        item2.setNumber(new PointNumber(7, 0));
        item2.setName("Куля свинцева деформована");
        items.add(item1);
        items.add(item2);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(items);
        given(itemsService.confirmParsed(anyList())).willReturn(2);
        this.mvc.perform(post("/api/items/addParsed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(2)));
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
    void addExistingCategoryTest() throws Exception {
        String json = "{\"id\":null, \"name\":\"Обладунки\", \"filters\":[\"Обладунок\"]}";
        List<String> filters = new ArrayList<>();
        filters.add("Обладунок");
        CategoryDto dto = new CategoryDto(null, "Обладунки", filters);
        given(this.itemsService.saveCategory(dto)).willThrow(SuchCategoryExistsException.class);

        this.mvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());


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
        given(this.itemsService.saveCategory(dto)).willThrow(NoSuchCategoryException.class);
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

    @Test
    void testExportItemsFiltered() throws Exception {
        given(itemExportService.exportItems(anyList(), anyList())).willReturn(new byte[]{0, 1, 2, 3});

        this.mvc.perform(post("/api/exportItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"ids\": [1, 2, 3], " +
                        "\"categories\": [\"Cat 1\", \"Cat 2\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{0, 1, 2, 3}));

        verify(itemExportService).exportItems(List.of(1, 2, 3), List.of("Cat 1", "Cat 2"));
    }

    @Test
    void testExportItemsIdsEmpty() throws Exception {
        given(itemExportService.exportItems(any(), any())).willReturn(new byte[]{1, 2, 3, 4});

        this.mvc.perform(post("/api/exportItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ids\": [], " +
                                "\"categories\": [\"Cat 1\", \"Cat 2\"]}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{1, 2, 3, 4}));

        verify(itemExportService).exportItems(isNull(), eq(List.of("Cat 1", "Cat 2")));
    }

    @Test
    void testExportItemsIdsAbsent() throws Exception {
        given(itemExportService.exportItems(any(), any())).willReturn(new byte[]{2, 3, 4, 5});

        this.mvc.perform(post("/api/exportItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"categories\": [\"Cat 1\", \"Cat 2\"] }"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{2, 3, 4, 5}));

        verify(itemExportService).exportItems(isNull(), eq(List.of("Cat 1", "Cat 2")));
    }

    @Test
    void testExportItemsCategoriesEmpty() throws Exception {
        given(itemExportService.exportItems(any(), any())).willReturn(new byte[]{3, 4, 5, 6});

        this.mvc.perform(post("/api/exportItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ids\": [1, 2, 3], " +
                                "\"categories\": []}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{3, 4, 5, 6}));

        verify(itemExportService).exportItems(eq(List.of(1, 2, 3)), isNull());
    }

    @Test
    void testExportItemsCategoriesAbsent() throws Exception {
        given(itemExportService.exportItems(any(), any())).willReturn(new byte[]{4, 5, 6, 7});

        this.mvc.perform(post("/api/exportItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ids\": [1, 2, 3] }"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{4, 5, 6, 7}));

        verify(itemExportService).exportItems(eq(List.of(1, 2, 3)), isNull());
    }

    @Test
    void testExportItemsEmptyFilteringObject() throws Exception {
        given(itemExportService.exportItems(any(), any())).willReturn(new byte[]{5, 6, 7, 8});

        this.mvc.perform(post("/api/exportItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{5, 6, 7, 8}));

        verify(itemExportService).exportItems(isNull(), isNull());
    }

    @Test
    void testExportItemsError() throws Exception {
        given(itemExportService.exportItems(any(), any()))
                .willThrow(new ItemsExportException(new RuntimeException("Something went wrong")));

        this.mvc.perform(post("/api/exportItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testExportBulletsFiltered() throws Exception {
        given(itemExportService.exportBullets(any())).willReturn(new byte[]{4, 3, 2, 1});

        this.mvc.perform(post("/api/exportBullets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ids\": [2, 3, 4]}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{4, 3, 2, 1}));

        verify(itemExportService).exportBullets(List.of(2, 3, 4));
    }

    @Test
    void testExportBulletsIdsEmpty() throws Exception {
        given(itemExportService.exportBullets(any())).willReturn(new byte[]{5, 4, 3, 2});

        this.mvc.perform(post("/api/exportBullets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ids\": []}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{5, 4, 3, 2}));

        verify(itemExportService).exportBullets(isNull());
    }

    @Test
    void testExportBulletsIdsAbsent() throws Exception {
        given(itemExportService.exportBullets(any())).willReturn(new byte[]{6, 5, 4, 3});

        this.mvc.perform(post("/api/exportBullets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{6, 5, 4, 3}));

        verify(itemExportService).exportBullets(isNull());
    }

    @Test
    void testExportBulletsError() throws Exception {
        given(itemExportService.exportBullets(any()))
                .willThrow(new ItemsExportException(new RuntimeException("Something went wrong")));

        this.mvc.perform(post("/api/exportBullets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError());
    }
}
