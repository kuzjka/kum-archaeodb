package ua.kuzjka.kumarchaeo.dto;

import ua.kuzjka.kumarchaeo.model.Item;
import ua.kuzjka.kumarchaeo.model.Location;

public class ItemDto {
    private Integer id;
    private String name;
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
    private String remarks;
    private String imageFile;

    private String category;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        id = item.getId();
        name = item.getName();
        year = item.getYear();
        number = item.getNumber();
        hectare = item.getHectare();
        depth = item.getDepth();
        location = item.getLocation();
        description = item.getDescription();
        material = item.getMaterial();
        technique = item.getTechnique();
        condition = item.getCondition();
        dimensions = item.getDimensions();
        weight = item.getWeight();
        remarks = item.getRemarks();
        imageFile = item.getImage();
        category = item.getCategory().getName();
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getHectare() {
        return hectare;
    }

    public void setHectare(Integer hectare) {
        this.hectare = hectare;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
