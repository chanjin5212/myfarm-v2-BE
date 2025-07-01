package com.myfarm.myfarm.adapter.`in`.web.orders.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetOrder {
    data class PathVariable(val id: UUID)

    data class Response(
        val id: UUID,
        val orderNumber: String?,
        val userId: UUID,
        val status: String,
        val shippingName: String,
        val shippingPhone: String,
        val shippingAddress: String,
        val shippingDetailAddress: String?,
        val shippingMemo: String?,
        val paymentMethod: String,
        val totalAmount: Int,
        val tid: String?,
        val cancelReason: String?,
        val cancelDate: LocalDateTime?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val orderItems: List<OrderItemInfo>
    )

    data class OrderItemInfo(
        val id: UUID,
        val productId: UUID,
        val productName: String,
        val productOptionId: UUID?,
        val optionName: String?,
        val quantity: Int,
        val price: Int,
        val totalPrice: Int,
        val options: String?,
        val createdAt: LocalDateTime
    )
}
