package com.myfarm.myfarm.domain.productreviews.service

import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.GetProductReview
import com.myfarm.myfarm.domain.productreviews.port.ProductReviewsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetProductReviewService(
    private val productReviewsRepository: ProductReviewsRepository
) {

    @Transactional(readOnly = true)
    fun getProductReview(id: UUID): GetProductReview.Response {
        val productReview = productReviewsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 리뷰입니다")

        return GetProductReview.Response(
            id = productReview.id,
            productId = productReview.productId,
            userId = productReview.userId,
            orderId = productReview.orderId,
            rating = productReview.rating,
            content = productReview.content,
            status = productReview.status,
            imageUrl = productReview.imageUrl,
            createdAt = productReview.createdAt,
            updatedAt = productReview.updatedAt
        )
    }
}
