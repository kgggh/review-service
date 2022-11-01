package com.app.review.repository;


import com.app.review.model.entity.Review;
import com.app.review.model.entity.ReviewReaction;
import com.app.review.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {
    Optional<ReviewReaction> findByReviewAndUser(Review review, User user);
}
