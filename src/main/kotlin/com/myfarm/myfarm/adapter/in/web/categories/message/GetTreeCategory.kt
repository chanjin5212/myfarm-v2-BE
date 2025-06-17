package com.myfarm.myfarm.adapter.`in`.web.categories.message

import java.util.UUID

abstract class GetTreeCategory {
    data class Request(
        val isActive: Boolean? = null
    )

    data class Response(
        val categories: List<CategoryTreeItem>
    )

    data class CategoryTreeItem(
        val id: UUID,
        val name: String,
        val description: String?,
        val isActive: Boolean,
        val children: List<CategoryTreeItem> = emptyList()
    )
}
