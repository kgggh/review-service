package com.app.review.repository.custom.impl;

import com.app.review.model.dto.ReviewSearchRequestDto;
import com.app.review.model.entity.QReview;
import com.app.review.model.entity.QReviewReaction;
import com.app.review.model.entity.QUser;
import com.app.review.model.entity.Review;
import com.app.review.repository.custom.ReviewCustomRepository;
import com.app.review.util.SearchHelper;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
