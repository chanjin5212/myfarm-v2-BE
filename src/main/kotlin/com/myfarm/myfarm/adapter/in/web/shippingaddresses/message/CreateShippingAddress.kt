package com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message

abstract class CreateShippingAddress {
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
