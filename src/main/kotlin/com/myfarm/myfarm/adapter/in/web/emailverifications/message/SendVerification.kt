package com.myfarm.myfarm.adapter.`in`.web.emailverifications.message

abstract class SendVerification {
    data class Request(
        val email: String,
        val verificationType: VerificationType
    )

    data class Response(
        val email: String,
        val message: String = "인증 코드가 발송되었습니다"
    )

    enum class VerificationType(val description: String) {
        REGISTRATION("회원가입"),
        FIND_ID("아이디 찾기"),
        FIND_PASSWORD("비밀번호 찾기")
    }
}
