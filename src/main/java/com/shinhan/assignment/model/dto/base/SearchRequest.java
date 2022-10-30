package com.shinhan.assignment.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private int size = 10;
    private int page = 0;
    private String sort;

    public int getSize() {
        return size < 10 ? 10 : size;
    }

    public int getPage() {
        return page < 0 ? 0 : page;
    }
}
