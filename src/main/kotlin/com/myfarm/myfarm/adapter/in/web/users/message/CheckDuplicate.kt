package com.myfarm.myfarm.adapter.`in`.web.users.message

abstract class CheckDuplicate {
    data class Request(
        val type: String,
        val value: String
    )

    data class Response(
        val available: Boolean,
        val message: String
    )
}
