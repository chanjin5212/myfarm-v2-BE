package com.myfarm.myfarm.adapter.`in`.web.payments.message

import java.util.UUID

abstract class CreatePayment {
    data class Request(
        val orderId: UUID,
        val paymentKey: String,
        val paymentMethod: String,
        val paymentProvider: String,
        val amount: Int,
        val status: String,
        val paymentDetails: Any? = null
    )

    data class Response(
        val success: Boolean,
        val paymentId: UUID,
        val message: String? = null
    )
}
