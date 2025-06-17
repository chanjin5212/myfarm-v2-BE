package com.myfarm.myfarm.domain.emailverifications.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "email_verifications")
data class EmailVerifications(
    @Id
    val id: UUID = UUID.randomUUID(),

    val email: String,

    val code: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val expiresAt: LocalDateTime,

    val verified: Boolean = false
)
