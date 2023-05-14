package ua.kuzjka.kumarchaeo.dto;

import java.util.List;

public class PageDto {

    private long totalCount;
    private long totalPages;
    private List<ItemDto> content;

    public PageDto() {
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<ItemDto> getContent() {
        return content;
    }

    public void setContent(List<ItemDto> content) {
        this.content = content;
    }
}
