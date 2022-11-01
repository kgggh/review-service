package com.app.review.util;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.app.review.model.dto.base.SearchRequest;
import org.springframework.data.domain.*;

public class SearchHelper {
    public static OrderSpecifier<?> sort(Path<?> parent, String sort){
        String[] sortArr = sort.replaceAll("\\s", "").split(",");
        if(sortArr.length < 2) {
            return new OrderSpecifier(Order.ASC, NullExpression.DEFAULT, OrderSpecifier.NullHandling.Default);
        }
        Path<Object> fieldPath = Expressions.path(Object.class, parent, sortArr[0]);
        return new OrderSpecifier(Order.valueOf(sortArr[1].toUpperCase()), fieldPath);
    }


    public static long getOffset(SearchRequest searchRequest) {
        return (long) searchRequest.getPage() * (long) searchRequest.getSize();
    }

    public static PageRequest of(SearchRequest searchRequest) {
        return PageRequest.of(searchRequest.getPage(), searchRequest.getSize());
    }
}
