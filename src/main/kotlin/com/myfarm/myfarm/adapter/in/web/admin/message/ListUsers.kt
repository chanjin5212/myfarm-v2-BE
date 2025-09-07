package com.myfarm.myfarm.adapter.`in`.web.admin.message

import java.time.LocalDateTime
import java.util.UUID

class ListUsers {
    data class Response(
        val users: List<UserSummary>
    )

    data class UserSummary(
        val id: UUID,
        val email: String,
        val name: String?,
        val isAdmin: Boolean,
        val isDeleted: Boolean,
        val createdAt: LocalDateTime
    )
}