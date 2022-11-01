package com.app.review.model.entity;

import com.app.review.model.entity.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Size(max = 1000, message = "길이 1000자까지")
    @Column(length = 1000)
    private String content;

    @Min(value = 1, message = "양수 1 ~ 5까지")
    @Max(value = 5, message = "양수 1 ~ 5까지")
    @Column(columnDefinition = "TINYINT", length=1)
    private int grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewReaction> reviewReactions = new ArrayList<>();

    private Review(String content, int grade, User user, Lecture lecture) {
        this.content = content;
        this.grade = grade;
        this.user = user;
        this.lecture = lecture;
    }

    public static Review createReview(String content, int grade, User user, Lecture lecture) {
        return new Review(content, grade, user, lecture);
    }

    public void modify(String content, int grade) {
        this.content = content;
        this.grade = grade;
    }

    public int getTotalRecommended() {
        return CollectionUtils.isEmpty(this.reviewReactions) ? 0 : this.reviewReactions.size();
    }

}
