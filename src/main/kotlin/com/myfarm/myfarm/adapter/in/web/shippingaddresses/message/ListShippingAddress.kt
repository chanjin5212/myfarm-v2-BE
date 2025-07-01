package com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message

import java.time.LocalDateTime
import java.util.UUID

abstract class ListShippingAddress {

    data class Request(
        val page: Int = 0,
        val size: Int = 10
    )

    data class Response(
        val totalCount: Int,
        val address: List<AddressInfo>
    )

    data class AddressInfo(
        val id: UUID,
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