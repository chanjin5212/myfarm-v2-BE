package com.myfarm.myfarm.adapter.`in`.web.payments.message

import java.util.UUID

abstract class UpdatePayment {
    data class PathVariable(val id: UUID)

    data class Request(
        val status: String? = null,
        val paymentDetails: Any? = null
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
