package com.myfarm.myfarm.adapter.`in`.web.productreviews.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetProductReview {
    data class PathVariable(val id: UUID)

    data class Response(
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
