package com.myfarm.myfarm.adapter.`in`.web.carts.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetCart {

    data class Response(
        val id: UUID,
        val userId: UUID,
        val items: List<CartItemInfo>,
        val totalQuantity: Int,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )

    data class CartItemInfo(
        val id: UUID,
        val productId: UUID,
        val productName: String,
        val productPrice: Int,
        val productThumbnailUrl: String?,
        val productOptionId: UUID?,
        val productOptionName: String?,
        val productOptionValue: String?,
        val additionalPrice: Int,
        val quantity: Int,
        val totalPrice: Int
    )
}
