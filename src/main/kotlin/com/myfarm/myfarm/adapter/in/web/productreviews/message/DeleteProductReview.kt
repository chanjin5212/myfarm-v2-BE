package com.myfarm.myfarm.adapter.`in`.web.productreviews.message

import java.util.UUID

abstract class DeleteProductReview {
    data class PathVariable(val id: UUID)

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
