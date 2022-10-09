package ua.kuzjka.kumarchaeo.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for bullet finds, which have some additional properties.
 */
@Entity
@DiscriminatorValue("B")
public class Bullet extends Item {
    private boolean deformed;
    private int caliber;

    /**
     * Checks if the bullet is deformed.
     * @return  {@code true} if item is deformed
     */
    public boolean isDeformed() {
        return deformed;
    }

    /**
     * Sets bullet deformation state.
     * @param deformed  {@code true} if the bullet is deformed
     */
    public void setDeformed(boolean deformed) {
        this.deformed = deformed;
    }

    /**
     * Gets bullet caliber.
     * @return  Bullet caliber in millimeters
     */
    public int getCaliber() {
        return caliber;
    }

    /**
     * Sets bullet caliber.
     * @param caliber   Bullet caliber in millimeters
     */
    public void setCaliber(int caliber) {
        this.caliber = caliber;
    }
}
