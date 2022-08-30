package ua.kuzjka.kumarchaeo.dto;

import ua.kuzjka.kumarchaeo.model.Category;

import java.util.List;

public class CategoryDto {
    private Integer id;
    private String name;
    private List<String> filters;

    public CategoryDto() {
    }

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.filters = category.getFilters();
    }

    public CategoryDto(Integer id, String name, List<String> filters) {
        this.id = id;
        this.name = name;
        this.filters = filters;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public Category toEntity() {
        Category category = new Category(name, filters);
        category.setId(id);
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDto that = (CategoryDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return filters != null ? filters.equals(that.filters) : that.filters == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (filters != null ? filters.hashCode() : 0);
        return result;
    }
}
