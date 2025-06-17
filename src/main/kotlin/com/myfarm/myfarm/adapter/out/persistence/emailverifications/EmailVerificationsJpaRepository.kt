package com.myfarm.myfarm.adapter.out.persistence.emailverifications

import com.myfarm.myfarm.domain.emailverifications.entity.EmailVerifications
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

interface EmailVerificationsJpaRepository : JpaRepository<EmailVerifications, UUID> {

    fun findByEmail(email: String): EmailVerifications?

    fun findByEmailAndCode(email: String, code: String): EmailVerifications?

    fun deleteByEmail(email: String)

    fun deleteByExpiresAtBefore(now: LocalDateTime)

    fun existsByEmail(email: String): Boolean
}
