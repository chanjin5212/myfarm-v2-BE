package com.myfarm.myfarm.adapter.`in`.web.users.message

abstract class FindPassword {
    data class Request(
        val email: String,
        val loginId: String
    )

    data class Response(
        val available: Boolean
    )
}
