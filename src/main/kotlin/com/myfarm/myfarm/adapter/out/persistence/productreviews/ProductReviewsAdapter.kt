package com.myfarm.myfarm.adapter.out.persistence.productreviews

import com.myfarm.myfarm.domain.productreviews.entity.ProductReviews
import com.myfarm.myfarm.domain.productreviews.entity.QProductReviews
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductReviewsAdapter(
    private val queryFactory: JPAQueryFactory
) {

    fun search(
        productId: UUID?,
        userId: UUID?,
        status: String?,
        rating: Int?,
        pageable: Pageable
    ): Page<ProductReviews> {
        val qProductReviews = QProductReviews.productReviews
        val builder = BooleanBuilder()

        // 상품 ID 필터
        productId?.let {
            builder.and(qProductReviews.productId.eq(it))
        }

        // 사용자 ID 필터
        userId?.let {
            builder.and(qProductReviews.userId.eq(it))
        }

        // 상태 필터
        status?.let {
            builder.and(qProductReviews.status.eq(it))
        }

        // 평점 필터
        rating?.let {
            builder.and(qProductReviews.rating.eq(it))
        }

        // 정렬 설정 (기본: 최신순)
        val orderSpecifier = qProductReviews.createdAt.desc()

        // 데이터 조회
        val reviews = queryFactory
            .selectFrom(qProductReviews)
            .where(builder)
            .orderBy(orderSpecifier)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // 총 개수 조회
        val total = queryFactory
            .select(qProductReviews.count())
            .from(qProductReviews)
            .where(builder)
            .fetchOne() ?: 0L

        return PageImpl(reviews, pageable, total)
    }
}
