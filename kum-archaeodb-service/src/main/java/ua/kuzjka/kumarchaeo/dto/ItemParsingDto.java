package ua.kuzjka.kumarchaeo.dto;

import ua.kuzjka.kumarchaeo.model.Location;

public class ItemParsingDto {
    private String name;
    private String number;
    private Location location;
    private Integer hectar;
    private String dimensions;
    private Integer weight;
    private String remarks;
    private String image;
    private String category;
    private Integer caliber;
    private boolean deformed;
    private boolean numberExists;
    private boolean autoDetectedCategory;
    private boolean autoDetectedCaliber;
    private boolean autoDetectedDeformed;
    private boolean autoDetectedHectar;
    private boolean save;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getHectar() {
        return hectar;
    }

    public void setHectar(Integer hectar) {
        this.hectar = hectar;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCaliber() {
        return caliber;
    }

    public void setCaliber(Integer caliber) {
        this.caliber = caliber;
    }

    public boolean isDeformed() {
        return deformed;
    }

    public void setDeformed(boolean deformed) {
        this.deformed = deformed;
    }

    public boolean isNumberExists() {
        return numberExists;
    }

    public void setNumberExists(boolean numberExists) {
        this.numberExists = numberExists;
    }

    public boolean isAutoDetectedCategory() {
        return autoDetectedCategory;
    }

    public void setAutoDetectedCategory(boolean autoDetectedCategory) {
        this.autoDetectedCategory = autoDetectedCategory;
    }

    public boolean isAutoDetectedCaliber() {
        return autoDetectedCaliber;
    }

    public void setAutoDetectedCaliber(boolean autoDetectedCaliber) {
        this.autoDetectedCaliber = autoDetectedCaliber;
    }

    public boolean isAutoDetectedDeformed() {
        return autoDetectedDeformed;
    }

    public void setAutoDetectedDeformed(boolean autoDetectedDeformed) {
        this.autoDetectedDeformed = autoDetectedDeformed;
    }

    public boolean isAutoDetectedHectar() {
        return autoDetectedHectar;
    }

    public void setAutoDetectedHectar(boolean autoDetectedHectar) {
        this.autoDetectedHectar = autoDetectedHectar;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
}
