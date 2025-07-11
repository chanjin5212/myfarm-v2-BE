package com.myfarm.myfarm.domain.productreviews.service

import com.myfarm.myfarm.adapter.`in`.web.productreviews.message.DeleteProductReview
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.productreviews.port.ProductReviewsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteProductReviewService(
    private val productReviewsRepository: ProductReviewsRepository
) {

    @Transactional
    fun deleteProductReview(userId: UUID, id: UUID): DeleteProductReview.Response {
        productReviewsRepository.findById(id)
            .checkOwnership(userId)

        productReviewsRepository.deleteById(id)

        return DeleteProductReview.Response(
            success = true,
            message = "리뷰가 삭제되었습니다"
        )
    }
}
