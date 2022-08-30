package ua.kuzjka.kumarchaeo.model;

import javax.persistence.*;
import java.util.List;

/**
 * Entity for category of finds.
 */
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    @ElementCollection
    @CollectionTable(
            name = "category_filters",
            joinColumns = @JoinColumn(name = "category_id")
    )
    @Column(name = "filter")
    private List<String> filters;

    /**
     * Creates new category.
     */
    public Category() {
    }

    /**
     * Creates new category.
     * @param name      Category name
     * @param filters   Autodetection filters for the category
     */
    public Category(String name, List<String> filters) {
        this.name = name;
        this.filters = filters;
    }

    /**
     * Gets entity ID.
     * @return  entity ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets entity ID
     * @param id    entity ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets category name.
     * @return  Name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets category name.
     * @param name  Name of the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets list of filters for category auto-detection.
     * @return  List of substrings to search in the item name
     */
    public List<String> getFilters() {
        return filters;
    }

    /**
     * Sets the list of filters for category auto-detection.
     * @param filters   List of substrings to search in the item name
     */
    public void setFilters(List<String> filters) {
        this.filters = filters;
    }


}
