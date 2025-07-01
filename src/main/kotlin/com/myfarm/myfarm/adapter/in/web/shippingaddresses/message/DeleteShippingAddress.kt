package com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message

import java.util.UUID

abstract class DeleteShippingAddress {
    data class PathVariable(val id: UUID)

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}