package com.myfarm.myfarm.domain.productreviews.service

import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.ListProductReview
import com.myfarm.myfarm.adapter.out.persistence.productreviews.ProductReviewsAdapter
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListProductReviewService(
    private val productReviewsAdapter: ProductReviewsAdapter
) {

    @Transactional(readOnly = true)
    fun listProductReviews(request: ListProductReview.Request): ListProductReview.Response {
        val pageable = PageRequest.of(request.page, request.size)

        val reviewPage = productReviewsAdapter.search(
            productId = request.productId,
            userId = request.userId,
            status = request.status,
            rating = request.rating,
            pageable = pageable
        )

        val reviewInfoList = reviewPage.content.map { review ->
            ListProductReview.ReviewInfo(
                id = review.id,
                productId = review.productId,
                userId = review.userId,
                orderId = review.orderId,
                rating = review.rating,
                content = review.content,
                status = review.status,
                imageUrl = review.imageUrl,
                createdAt = review.createdAt,
                updatedAt = review.updatedAt
            )
        }

        return ListProductReview.Response(
            totalCount = reviewPage.totalElements.toInt(),
            reviews = reviewInfoList
        )
    }
}
