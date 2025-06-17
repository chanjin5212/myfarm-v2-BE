package com.myfarm.myfarm.domain.emailverifications.port

import com.myfarm.myfarm.adapter.out.persistence.emailverifications.EmailVerificationsJpaRepository
import com.myfarm.myfarm.domain.emailverifications.entity.EmailVerifications
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EmailVerificationsRepository(
    private val emailVerificationsJpaRepository: EmailVerificationsJpaRepository
) {

    fun findByEmail(email: String): EmailVerifications? {
        return emailVerificationsJpaRepository.findByEmail(email)
    }

    fun findByEmailAndCode(email: String, code: String): EmailVerifications? {
        return emailVerificationsJpaRepository.findByEmailAndCode(email, code)
    }

    fun save(emailVerification: EmailVerifications): EmailVerifications {
        return emailVerificationsJpaRepository.save(emailVerification)
    }

    fun deleteByEmail(email: String) {
        emailVerificationsJpaRepository.deleteByEmail(email)
    }

    fun deleteExpiredVerifications() {
        emailVerificationsJpaRepository.deleteByExpiresAtBefore(LocalDateTime.now())
    }

    fun existsByEmail(email: String): Boolean {
        return emailVerificationsJpaRepository.existsByEmail(email)
    }
}
