package com.myfarm.myfarm.adapter.`in`.web.categories.message

import java.util.UUID

abstract class UpdateCategory {

    data class PathVariable(val id: UUID)

    data class Request(
        val name: String,
        val parentId: UUID? = null,
        val description: String? = null,
        val isActive: Boolean = true
    )

    data class Response(
        val success: Boolean
    )
}
