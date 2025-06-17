package com.myfarm.myfarm.adapter.`in`.web.categories.message

import java.util.UUID

abstract class CreateCategory {
    data class Request(
        val name: String,
        val parentId: UUID? = null,
        val description: String? = null
    )

    data class Response(
        val success: Boolean
    )
}