package ua.kuzjka.kumarchaeo.export;

import ua.kuzjka.kumarchaeo.model.Bullet;
import ua.kuzjka.kumarchaeo.model.BulletStandard;
import ua.kuzjka.kumarchaeo.model.Deformation;

/**
 * Represents a row in an exported CSV with bullets.
 */
public class BulletExportEntry extends ItemExportEntry {
    private int caliber;
    private boolean approximate;
    private BulletStandard standard;
    private Deformation deformed;
    private boolean imprints;

    /**
     * Creates an entry from a bullet entity.
     * @param bullet    Bullet object
     */
    public BulletExportEntry(Bullet bullet) {
        super(bullet);
        this.caliber = bullet.getCaliber();
        this.approximate = bullet.isApproximate();
        this.standard = bullet.getStandard();
        this.deformed = bullet.getDeformed();
        this.imprints = bullet.hasImprints();
    }

    public int getCaliber() {
        return caliber;
    }

    public boolean isApproximate() {
        return approximate;
    }

    public BulletStandard getStandard() {
        return standard;
    }

    public Deformation getDeformed() {
        return deformed;
    }

    public boolean isImprints() {
        return imprints;
    }
}
