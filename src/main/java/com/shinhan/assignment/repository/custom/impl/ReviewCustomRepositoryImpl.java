package com.shinhan.assignment.repository.custom.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.assignment.model.dto.ReviewSearchRequestDto;
import com.shinhan.assignment.model.entity.QReview;
import com.shinhan.assignment.model.entity.QReviewReaction;
import com.shinhan.assignment.model.entity.QUser;
import com.shinhan.assignment.model.entity.Review;
import com.shinhan.assignment.repository.custom.ReviewCustomRepository;
import com.shinhan.assignment.util.SearchHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.List;

@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {
    private final JPAQueryFactory query;
    private final QReview review = QReview.review;
    private final QReviewReaction reviewReaction = QReviewReaction.reviewReaction;
    private final QUser user = QUser.user;

    @Override
    public Page<Review> findAll(ReviewSearchRequestDto reviewSearchRequestDto) {
        List<Review> result = query.select(review)
                .from(review)
                .leftJoin(review.user, user)
                .fetchJoin()
                .leftJoin(review.reviewReactions, reviewReaction)
                .fetchJoin()
                .distinct()
                .orderBy(getOrder(reviewSearchRequestDto))
                .offset(SearchHelper.getOffset(reviewSearchRequestDto))
                .limit(reviewSearchRequestDto.getSize())
                .fetch();

        JPAQuery<Review> count = query.select(review)
                .from(review)
                .leftJoin(review.user, user)
                .fetchJoin()
                .leftJoin(review.reviewReactions, reviewReaction)
                .fetchJoin()
                .distinct();

        return PageableExecutionUtils.getPage(result, SearchHelper.of(reviewSearchRequestDto), () ->count.fetch().size());
    }

    private OrderSpecifier<?> getOrder(ReviewSearchRequestDto reviewSearchRequestDto) {
        if(Strings.isBlank(reviewSearchRequestDto.getSort())) {
            return review.reviewReactions.size().desc();
        }
        return SearchHelper.sort(review._super, reviewSearchRequestDto.getSort());
    }
}
