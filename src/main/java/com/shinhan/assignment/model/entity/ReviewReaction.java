package com.shinhan.assignment.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReviewReaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_review_id")
    private Review review;

    private ReviewReaction(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public static ReviewReaction createReviewReaction(User user, Review review) {
        return new ReviewReaction(user, review);
    }
}
