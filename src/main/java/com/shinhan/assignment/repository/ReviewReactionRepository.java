package com.shinhan.assignment.repository;


import com.shinhan.assignment.model.entity.Review;
import com.shinhan.assignment.model.entity.ReviewReaction;
import com.shinhan.assignment.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {
    Optional<ReviewReaction> findByReviewAndUser(Review review, User user);
}
