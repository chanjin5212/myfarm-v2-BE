package com.myfarm.myfarm.domain.emailverifications.service

import com.myfarm.myfarm.adapter.`in`.web.emailverifications.message.VerifyEmail
import com.myfarm.myfarm.domain.emailverifications.port.EmailVerificationsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class VerifyEmailService(
    private val emailVerificationsRepository: EmailVerificationsRepository
) {

    @Transactional
    fun verifyEmail(request: VerifyEmail.Request): VerifyEmail.Response {
        val verification = emailVerificationsRepository.findByEmailAndCode(request.email, request.code)
            ?: throw IllegalArgumentException("잘못된 인증 코드입니다")

        if (verification.expiresAt.isBefore(LocalDateTime.now())) {
            emailVerificationsRepository.deleteByEmail(request.email)
            throw IllegalArgumentException("인증 코드가 만료되었습니다")
        }

        if (verification.verified) {
            throw IllegalArgumentException("이미 인증된 이메일입니다")
        }

        val verifiedEmail = verification.copy(verified = true)
        emailVerificationsRepository.save(verifiedEmail)

        return VerifyEmail.Response(
            email = request.email
        )
    }
}
