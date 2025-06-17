package com.myfarm.myfarm.adapter.`in`.web.categories.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetCategory {

    data class PathVariable(val id: UUID)

    data class Response(
        val id: UUID,
        val name: String,
        val parentId: UUID?,
        val description: String?,
        val isActive: Boolean,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
