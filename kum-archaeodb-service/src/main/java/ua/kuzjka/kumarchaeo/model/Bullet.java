package ua.kuzjka.kumarchaeo.model;

import javax.persistence.*;

/**
 * Entity for bullet finds, which have some additional properties.
 */
@Entity
@DiscriminatorValue("B")
public class Bullet extends Item {
    private int caliber;
    private boolean approximate;

    @Enumerated(EnumType.STRING)
    private BulletStandard standard;
    @Enumerated(EnumType.STRING)
    private Deformation deformed;

    private boolean imprints;

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

    /**
     * Checks if caliber is approximate.
     * @return  {@code true} if caliber is approximate, false otherwise
     */
    public boolean isApproximate() {
        return approximate;
    }

    /**
     * Sets approximate caliber flag.
     * @param approximate   {@code true} if the caliber is approximate
     */
    public void setApproximate(boolean approximate) {
        this.approximate = approximate;
    }

    /**
     * Gets bullet standard class.
     * @return  Bullet standard class
     */
    public BulletStandard getStandard() {
        return standard;
    }

    /**
     * Sets bullet standard class.
     * @param standard  Bullet standard class
     */
    public void setStandard(BulletStandard standard) {
        this.standard = standard;
    }

    /**
     * Gets bullet deformation degree.
     * @return  Bullet deformation degree
     */
    public Deformation getDeformed() {
        return deformed;
    }

    /**
     * Sets bullet deformation degree.
     * @param deformed  Bullet deformation degree
     */
    public void setDeformed(Deformation deformed) {
        this.deformed = deformed;
    }

    /**
     * Checks if the bullet has imprints.
     * @return  {@code true} if the bullet has imprints, {@code false} otherwise
     */
    public boolean hasImprints() {
        return imprints;
    }

    /**
     * Sets existence of imprints on the bullet.
     * @param imprints  {@code true} if the bullet has imprints
     */
    public void setImprints(boolean imprints) {
        this.imprints = imprints;
    }
}
