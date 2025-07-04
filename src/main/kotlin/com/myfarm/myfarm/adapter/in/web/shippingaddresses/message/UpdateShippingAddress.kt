package com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message

import java.util.UUID

abstract class UpdateShippingAddress {
    data class PathVariable(val id: UUID)

    data class Request(
        val recipientName: String,
        val phone: String,
        val address: String,
        val detailAddress: String? = null,
        val isDefault: Boolean = false,
        val memo: String? = null
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
