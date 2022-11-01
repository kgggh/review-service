package com.app.review.model.dto.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class SearchResponse<T> {
    private T data;
    private int size;
    private int page;
    private int totalPage;
    private Long count;

    public SearchResponse(Page<T> page) {
        this.size = page.getSize();
        this.page = page.getPageable().getPageNumber();
        this.totalPage = page.getTotalPages();
        this.count = page.getTotalElements();
        this.data = (T) page.getContent();
    }
}
