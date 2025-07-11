package com.myfarm.myfarm.adapter.`in`.web.productreviews.message

import java.util.UUID

abstract class UpdateProductReview {
    data class PathVariable(val id: UUID)

    data class Request(
        val rating: Int? = null,
        val content: String? = null,
        val imageUrl: String? = null
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
