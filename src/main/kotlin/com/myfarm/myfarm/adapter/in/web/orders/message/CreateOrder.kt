package com.myfarm.myfarm.adapter.`in`.web.orders.message

import java.util.UUID

abstract class CreateOrder {
    data class Request(
        val shippingName: String,
        val shippingPhone: String,
        val shippingAddress: String,
        val shippingDetailAddress: String? = null,
        val shippingMemo: String? = null,
        val paymentMethod: String,
        val orderItems: List<OrderItemRequest>
    )

    data class OrderItemRequest(
        val productId: UUID,
        val productOptionId: UUID, // nullable 제거
        val quantity: Int,
        val price: Int,
        val options: String? = null
    )

    data class Response(
        val success: Boolean,
        val orderId: UUID? = null,
        val orderNumber: String? = null
    )
}
