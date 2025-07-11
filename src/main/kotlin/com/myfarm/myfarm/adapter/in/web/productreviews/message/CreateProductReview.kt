package com.myfarm.myfarm.adapter.`in`.web.productreviews.message

import java.util.UUID

abstract class CreateProductReview {
    data class Request(
        val productId: UUID,
        val orderId: UUID? = null,
        val rating: Int,
        val content: String,
        val imageUrl: String? = null
    )

    data class Response(
        val success: Boolean,
        val reviewId: UUID,
        val message: String? = null
    )
}
