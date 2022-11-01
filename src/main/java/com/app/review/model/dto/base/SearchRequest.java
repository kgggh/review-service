package com.app.review.model.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    @Schema(example = "10")
    private int size = 10;

    @Schema(example = "0")
    private int page = 0;

    @Schema(example = "id,DESC", description = "field,SORT")
    private String sort;

    public int getSize() {
        return size < 10 ? 10 : size;
    }

    public int getPage() {
        return page < 0 ? 0 : page;
    }
}
