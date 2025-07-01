package com.myfarm.myfarm.adapter.`in`.web.orders.message

import java.util.UUID

abstract class CancelOrder {
    data class PathVariable(val id: UUID)

    data class Request(
        val cancelReason: String
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
