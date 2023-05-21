package ua.kuzjka.kumarchaeo.dto;

import java.util.List;

/**
 * Represents the body of bullets export request
 */
public class BulletExportRequest {
    private List<Integer> ids;

    /**
     * Creates new request body.
     */
    public BulletExportRequest() {
    }

    /**
     * Creates request body.
     * @param ids   Item IDs to include
     */
    public BulletExportRequest(List<Integer> ids) {
        this.ids = ids;
    }

    /**
     * Gets list of item IDs to select.
     * @return  List of IDs. If empty or {@code null} - no filtering is assumed
     */
    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
