package com.app.review.repository;

import com.app.review.model.entity.Review;
import com.app.review.repository.custom.ReviewCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
}
