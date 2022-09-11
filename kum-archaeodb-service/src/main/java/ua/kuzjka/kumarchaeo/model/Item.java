package ua.kuzjka.kumarchaeo.model;

import javax.persistence.*;

/**
 * Entity for an archaeological find.
 */
@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;
    @Column(name = "year_")
    private int year;
    private String number;
    private Integer hectare;
    private Integer depth;
    private Location location;
    private String description;
    private String material;
    private String technique;
    private String condition;
    private String dimensions;
    private Integer weight;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String remarks;
    private String image;

    /**
     * Gets entity ID.
     * @return Entity ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets entity ID.
     * @param id    Entity ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets item name.
     * @return  Name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets item name.
     * @param name  Name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets finding year.
     * @return  Finding year of the item
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets finding year.
     * @param year  Finding year of the item
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets inventory list number.
     * @return  Number in the inventory list
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets inventory list number.
     * @param number    Number in the inventory list
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Gets hectare where the item was found.
     * @return  Hectare number
     */
    public Integer getHectare() {
        return hectare;
    }

    /**
     * Sets hectare where the item was found.
     * @param hectare   Hectare number
     */
    public void setHectare(Integer hectare) {
        this.hectare = hectare;
    }

    /**
     * Gets depth of the item.
     * @return  Depth in centimeters
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * Sets depth of the item.
     * @param depth Depth in centimeters
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * Gets item find location.
     * @return  Item location coordinates.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets item find location.
     * @param location  Item location coordinates.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets item description.
     * @return  Textual description for the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets item description.
     * @param description Textual description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets item material.
     * @return  Crafting material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Sets item material.
     * @param material  Crafting material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Gets item crafting technique.
     * @return  Crafting technique
     */
    public String getTechnique() {
        return technique;
    }

    /**
     * Sets item crafting technique.
     * @param technique Crafting technique
     */
    public void setTechnique(String technique) {
        this.technique = technique;
    }

    /**
     * Gets item condition.
     * @return  Preservation condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets item condition.
     * @param condition Preservation condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Gets item dimensions.
     * @return  Item dimensions (free textual representation)
     */
    public String getDimensions() {
        return dimensions;
    }

    /**
     * Sets item dimensions.
     * @param dimensions    Item dimensions (free textual representation)
     */
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Gets item weight.
     * @return  Item weight in grams
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * Stes item weight.
     * @param weight    Item weight in grams
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Gets item category.
     * @return  Item category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets item category.
     * @param category  Item category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets remarks about this item.
     * @return  Remarks contents
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets remarks about this item.
     * @param remarks   Remarks contents
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Gets image filename.
     * @return  Name of the image file
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image filename.
     * @param image Name of the image file
     */
    public void setImage(String image) {
        this.image = image;
    }
}
