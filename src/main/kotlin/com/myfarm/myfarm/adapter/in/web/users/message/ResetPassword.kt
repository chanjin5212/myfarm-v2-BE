package com.myfarm.myfarm.adapter.`in`.web.users.message

abstract class ResetPassword {
    data class Request(
        val email: String,
        val loginId: String,
        val newPassword: String
    )

    data class Response(
        val success: Boolean,
        val message: String = "비밀번호가 성공적으로 변경되었습니다"
    )
}
