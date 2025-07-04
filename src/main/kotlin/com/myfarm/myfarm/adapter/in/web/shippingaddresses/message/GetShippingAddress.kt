package com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetShippingAddress {

    data class PathVariable(val id: UUID)

    data class Response(
        val id: UUID,
        val userId: UUID,
        val recipientName: String,
        val phone: String,
        val address: String,
        val detailAddress: String?,
        val isDefault: Boolean,
        val memo: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
