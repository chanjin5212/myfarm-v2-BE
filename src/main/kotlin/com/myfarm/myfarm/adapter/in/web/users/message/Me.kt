package com.myfarm.myfarm.adapter.`in`.web.users.message

import java.time.LocalDateTime
import java.util.UUID

class Me {
    data class Response(
        val id: UUID,
        val email: String,
        val loginId: String,
        val name: String,
        val nickname: String?,
        val phoneNumber: String,
        val postcode: String?,
        val address: String?,
        val detailAddress: String?,
        val avatarUrl: String?,
        val termsAgreed: Boolean,
        val marketingAgreed: Boolean,
        val createdAt: LocalDateTime,
        val lastLogin: LocalDateTime?
    )
}
