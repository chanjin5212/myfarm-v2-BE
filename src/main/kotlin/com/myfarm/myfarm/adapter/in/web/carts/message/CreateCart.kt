package com.myfarm.myfarm.adapter.`in`.web.carts.message

import java.util.UUID

abstract class CreateCart {
    data class Request(
        val items: List<CartItemRequest>
    )

    data class CartItemRequest(
        val productId: UUID,
        val productOptionId: UUID,
        val quantity: Int = 1
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
