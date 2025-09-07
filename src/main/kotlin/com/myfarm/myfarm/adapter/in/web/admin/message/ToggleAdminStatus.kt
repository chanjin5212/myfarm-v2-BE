package com.myfarm.myfarm.adapter.`in`.web.admin.message

import java.util.UUID

class ToggleAdminStatus {
    data class Response(
        val message: String,
        val userId: UUID,
        val isAdmin: Boolean
    )
}