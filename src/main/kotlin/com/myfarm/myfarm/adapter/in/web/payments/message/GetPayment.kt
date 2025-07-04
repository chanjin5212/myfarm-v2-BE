package com.myfarm.myfarm.adapter.`in`.web.payments.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetPayment {
    data class PathVariable(val id: UUID)

    data class Response(
        val id: UUID,
        val orderId: UUID,
        val paymentKey: String,
        val paymentMethod: String,
        val paymentProvider: String,
        val amount: Int,
        val status: String,
        val paymentDetails: Any?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
