package com.myfarm.myfarm.adapter.`in`.web.users.message

abstract class FindId {
    data class Request(
        val email: String
    )

    data class Response(
        val loginId: String
    )
}
