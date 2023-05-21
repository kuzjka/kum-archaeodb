package ua.kuzjka.kumarchaeo.dto;

import java.util.List;

/**
 * Represents the body of the item export request.
 */
public class ItemExportRequest {
    private List<Integer> ids;
    private List<String> categories;

    /**
     * Creates empty request body.
     */
    public ItemExportRequest() {
    }

    /**
     * Creates request body.
     * @param ids           Item IDs to select
     * @param categories    Item categories to include
     */
    public ItemExportRequest(List<Integer> ids, List<String> categories) {
        this.ids = ids;
        this.categories = categories;
    }

    /**
     * Gets item IDs.
     * @return  IDs of the items to be included
     */
    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    /**
     * Gets item categories to include.
     * @return  Item categories.
     */
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
