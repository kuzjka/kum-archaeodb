package ua.kuzjka.kumarchaeo.export;

import ua.kuzjka.kumarchaeo.model.Item;

/**
 * Represents a row in an exported CSV with items.
 */
public class ItemExportEntry {
    private String pointNumber;
    private String name;
    private String category;
    private int year;
    private Integer hectare;
    private Integer depth;
    private float latitude;
    private float longitude;
    private Float weight;
    private Integer context;

    /**
     * Creates new entry from an item entity.
     * @param item  Item object
     */
    public ItemExportEntry(Item item) {
        this.pointNumber = item.getPointNumber().toString();
        this.name = item.getName();
        this.category = item.getCategory().getName();
        this.year = item.getYear();
        this.hectare = item.getHectare();
        this.depth = item.getDepth();
        this.latitude = item.getLocation().getLatitude();
        this.longitude = item.getLocation().getLongitude();
        this.weight = item.getWeight();
        this.context = item.getContext();
    }

    public String getPointNumber() {
        return pointNumber;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getYear() {
        return year;
    }

    public Integer getHectare() {
        return hectare;
    }

    public Integer getDepth() {
        return depth;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Float getWeight() {
        return weight;
    }

    public Integer getContext() {
        return context;
    }
}
