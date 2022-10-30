package com.shinhan.assignment.repository.custom;

import com.shinhan.assignment.model.dto.ReviewSearchRequestDto;
import com.shinhan.assignment.model.entity.Review;
import org.springframework.data.domain.Page;

public interface ReviewCustomRepository {
    Page<Review> findAll(ReviewSearchRequestDto reviewSearchRequestDto);
}
