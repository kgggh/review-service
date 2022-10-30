package com.shinhan.assignment.repository;

import com.shinhan.assignment.model.entity.Review;
import com.shinhan.assignment.repository.custom.ReviewCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
}
