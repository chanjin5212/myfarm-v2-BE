package com.myfarm.myfarm.adapter.`in`.web.orders.message

import java.time.LocalDateTime
import java.util.UUID

abstract class ListOrder {
    data class Request(
        val status: String? = null,
        val startDate: String? = null,
        val endDate: String? = null,
        val sortBy: String = "latest",
        val page: Int = 0,
        val size: Int = 20
    )

    data class Response(
        val totalCount: Int,
        val orders: List<OrderSummary>
    )

    data class OrderSummary(
        val id: UUID,
        val orderNumber: String?,
        val status: String,
        val totalAmount: Int,
        val itemCount: Int,
        val createdAt: LocalDateTime
    )
}
