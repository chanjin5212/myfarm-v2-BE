package com.myfarm.myfarm.adapter.`in`.web.categories.message

import java.util.UUID

abstract class DeleteCategory {

    data class PathVariable(val id: UUID)

    data class Response(
        val success: Boolean
    )
}
