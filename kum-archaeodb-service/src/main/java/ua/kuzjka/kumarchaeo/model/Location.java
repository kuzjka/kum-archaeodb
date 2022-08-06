package ua.kuzjka.kumarchaeo.model;

import javax.persistence.Embeddable;

/**
 * Represents a geolocation.
 */
@Embeddable
public class Location {
    /**
     * Creates a location with coordinates 0;0.
     */
    public Location() {
    }

    /**
     * Creates new location.
     * @param latitude      Latitude in degrees, positive for the Northern Hemisphere
     * @param longitude     Longitude in degrees, positive for the Eastern Hemisphere
     */
    public Location(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private float latitude;
    private float longitude;

    /**
     * Gets latitude.
     * @return Latitude in degrees. Positive numbers for the Northern Hemisphere
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     * @param latitude  Latitude in degrees. Positive numbers for the Northern Hemisphere
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     * @return  Longitude in degrees. Positive numbers for the Eastern Hemisphere
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     * @param longitude Longitude in degrees. Positive numbers for the Eastern Hemisphere
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
