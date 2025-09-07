package com.myfarm.myfarm.adapter.`in`.web.admin.message

import java.util.UUID

class GetDashboard {
    data class Response(
        val message: String,
        val userId: UUID,
        val totalUsers: Long
    )
}