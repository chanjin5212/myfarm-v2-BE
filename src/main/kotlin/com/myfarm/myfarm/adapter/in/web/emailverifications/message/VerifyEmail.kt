package com.myfarm.myfarm.adapter.`in`.web.emailverifications.message

abstract class VerifyEmail {
    data class Request(
        val email: String,
        val code: String
    )

    data class Response(
        val email: String,
        val verified: Boolean = true,
        val message: String = "이메일 인증이 완료되었습니다"
    )
}
