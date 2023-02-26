package ua.kuzjka.kumarchaeo.dto;

import ua.kuzjka.kumarchaeo.model.Deformation;
import ua.kuzjka.kumarchaeo.model.Location;
import ua.kuzjka.kumarchaeo.model.PointNumber;

public class ItemParsingDto {
    private String name;
    private PointNumber number;
    private Location location;
    private Integer hectar;
    private String dimensions;
    private Float weight;
    private String remarks;
    private String gpsPoint;
    private String category;
    private Integer caliber;
    private boolean caliberApproximate;
    private Deformation deformation;
    private boolean numberExists;
    private boolean categoryAutodetected;
    private boolean caliberAutodetected;
    private boolean deformationAutodetected;
    private boolean hectarAutodetected;
    private boolean save;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PointNumber getNumber() {
        return number;
    }

    public void setNumber(PointNumber number) {
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getGpsPoint() {
        return gpsPoint;
    }

    public void setGpsPoint(String gpsPoint) {
        this.gpsPoint = gpsPoint;
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

    public boolean isCaliberApproximate() {
        return caliberApproximate;
    }

    public void setCaliberApproximate(boolean caliberApproximate) {
        this.caliberApproximate = caliberApproximate;
    }

    public Deformation getDeformation() {
        return deformation;
    }

    public void setDeformation(Deformation deformation) {
        this.deformation = deformation;
    }

    public boolean isNumberExists() {
        return numberExists;
    }

    public void setNumberExists(boolean numberExists) {
        this.numberExists = numberExists;
    }

    public boolean isCategoryAutodetected() {
        return categoryAutodetected;
    }

    public void setCategoryAutodetected(boolean categoryAutodetected) {
        this.categoryAutodetected = categoryAutodetected;
    }

    public boolean isCaliberAutodetected() {
        return caliberAutodetected;
    }

    public void setCaliberAutodetected(boolean caliberAutodetected) {
        this.caliberAutodetected = caliberAutodetected;
    }

    public boolean isDeformationAutodetected() {
        return deformationAutodetected;
    }

    public void setDeformationAutodetected(boolean deformationAutodetected) {
        this.deformationAutodetected = deformationAutodetected;
    }

    public boolean isHectarAutodetected() {
        return hectarAutodetected;
    }

    public void setHectarAutodetected(boolean hectarAutodetected) {
        this.hectarAutodetected = hectarAutodetected;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
}
