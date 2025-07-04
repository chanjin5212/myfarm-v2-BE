package com.myfarm.myfarm.adapter.`in`.web.users.message

abstract class Register {
    data class Request(
        val email: String,
        val loginId: String,
        val password: String,
        val name: String,
        val nickname: String? = null,
        val phoneNumber: String? = null,
        val postcode: String? = null,
        val address: String? = null,
        val detailAddress: String? = null,
        val termsAgreed: Boolean,
        val marketingAgreed: Boolean = false
    )

    data class Response(
        val email: String,
        val loginId: String
    )
}