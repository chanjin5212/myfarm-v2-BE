package com.myfarm.myfarm.adapter.`in`.web.emailverifications.message

abstract class SendVerification {
    data class Request(
        val email: String
    )

    data class Response(
        val email: String,
        val message: String = "인증 코드가 발송되었습니다"
    )
}
