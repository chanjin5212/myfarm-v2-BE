package com.myfarm.myfarm.adapter.`in`.web.payments.message

import java.util.UUID

abstract class DeletePayment {
    data class Request(
        val paymentIds: List<UUID>
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
