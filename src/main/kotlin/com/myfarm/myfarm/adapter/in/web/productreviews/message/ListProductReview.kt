package com.myfarm.myfarm.adapter.`in`.web.productreviews.message

import java.time.LocalDateTime
import java.util.UUID

abstract class ListProductReview {
    data class Request(
        val productId: UUID? = null,
        val userId: UUID? = null,
        val status: String? = null,
        val rating: Int? = null,
        val page: Int = 0,
        val size: Int = 20
    )

    data class Response(
        val totalCount: Int,
        val reviews: List<ReviewInfo>
    )

    data class ReviewInfo(
        val id: UUID,
        val productId: UUID,
        val userId: UUID,
        val orderId: UUID?,
        val rating: Int,
        val content: String,
        val status: String,
        val imageUrl: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
