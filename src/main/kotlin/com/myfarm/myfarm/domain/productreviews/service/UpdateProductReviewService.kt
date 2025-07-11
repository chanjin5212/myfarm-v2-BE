package com.myfarm.myfarm.domain.productreviews.service

import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.UpdateProductReview
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.productreviews.port.ProductReviewsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateProductReviewService(
    private val productReviewsRepository: ProductReviewsRepository
) {

    @Transactional
    fun updateProductReview(userId: UUID, id: UUID, request: UpdateProductReview.Request): UpdateProductReview.Response {
        val existingReview = productReviewsRepository.findById(id)
            .checkOwnership(userId)

        request.rating?.let { rating ->
            if (rating < 1 || rating > 5) {
                throw IllegalArgumentException("평점은 1~5 사이의 값이어야 합니다")
            }
        }

        request.content?.let { content ->
            if (content.isBlank()) {
                throw IllegalArgumentException("리뷰 내용을 입력해주세요")
            }
        }

        val updatedReview = existingReview.copy(
            rating = request.rating ?: existingReview.rating,
            content = request.content?.trim() ?: existingReview.content,
            imageUrl = request.imageUrl ?: existingReview.imageUrl,
            updatedAt = LocalDateTime.now()
        )

        productReviewsRepository.save(updatedReview)

        return UpdateProductReview.Response(
            success = true,
            message = "리뷰가 수정되었습니다"
        )
    }
}
