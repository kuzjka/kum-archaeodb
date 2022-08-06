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
    private int id;

    private String name;

    @ElementCollection
    @CollectionTable(
            name = "category_filters",
            joinColumns = @JoinColumn(name = "category_id")
    )
    @Column(name = "filter")
    private List<String> filters;

    /**
     * Gets entity ID.
     * @return  entity ID
     */
    public int getId() {
        return id;
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
