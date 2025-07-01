package com.myfarm.myfarm.adapter.`in`.web.carts.message

abstract class DeleteCart {

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
