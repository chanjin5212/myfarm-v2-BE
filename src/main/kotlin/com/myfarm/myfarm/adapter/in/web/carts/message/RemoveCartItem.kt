package com.myfarm.myfarm.adapter.`in`.web.carts.message

import java.util.UUID

abstract class RemoveCartItem {
    data class Request(
        val items: List<RemoveCartItemRequest>
    )

    data class RemoveCartItemRequest(
        val productId: UUID,
        val productOptionId: UUID
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
