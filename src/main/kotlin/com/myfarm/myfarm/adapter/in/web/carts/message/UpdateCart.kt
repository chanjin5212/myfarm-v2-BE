package com.myfarm.myfarm.adapter.`in`.web.carts.message

import java.util.UUID

abstract class UpdateCart {
    data class Request(
        val items: List<UpdateCartItemRequest>
    )

    data class UpdateCartItemRequest(
        val productId: UUID,
        val productOptionId: UUID,
        val quantity: Int
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
