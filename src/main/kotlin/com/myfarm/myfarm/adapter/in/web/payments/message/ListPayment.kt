package com.myfarm.myfarm.adapter.`in`.web.payments.message

import java.time.LocalDateTime
import java.util.UUID

abstract class ListPayment {
    data class Request(
        val status: String? = null,
        val paymentProvider: String? = null,
        val paymentMethod: String? = null,
        val page: Int = 0,
        val size: Int = 20
    )

    data class Response(
        val totalCount: Int,
        val payments: List<PaymentInfo>
    )

    data class PaymentInfo(
        val id: UUID,
        val orderId: UUID,
        val paymentKey: String,
        val paymentMethod: String,
        val paymentProvider: String,
        val amount: Int,
        val status: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
