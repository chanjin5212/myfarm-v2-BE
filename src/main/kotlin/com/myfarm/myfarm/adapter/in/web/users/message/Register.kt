package com.myfarm.myfarm.adapter.`in`.web.users.message

abstract class Register {
    data class Request(
        val email: String,
        val loginId: String,
        val password: String,
        val name: String,
        val phoneNumber: String? = null,
        val termsAgreed: Boolean,
        val marketingAgreed: Boolean = false
    )

    data class Response(
        val email: String,
        val loginId: String
    )
}
