package com.myfarm.myfarm.domain.users.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "users")
data class Users(
    @Id
    val id: UUID = UUID.randomUUID(),

    val email: String,

    val name: String? = null,

    val avatarUrl: String? = null,

    val termsAgreed: Boolean = false,

    val marketingAgreed: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now(),

    val googleId: String? = null,

    val kakaoId: String? = null,

    val naverId: String? = null,

    val lastLogin: LocalDateTime? = null,

    val nickname: String? = null,

    val loginId: String? = null,

    val password: String? = null,

    val phoneNumber: String? = null,

    val postcode: String? = null,

    val address: String? = null,

    val detailAddress: String? = null,

    val isAdmin: Boolean = false,

    val isDeleted: Boolean = false,

    val deletedAt: LocalDateTime? = null
)
