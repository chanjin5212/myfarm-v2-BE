package com.myfarm.myfarm.domain.emailverifications.service

import com.myfarm.myfarm.adapter.`in`.web.emailverifications.message.SendVerification
import com.myfarm.myfarm.domain.emailverifications.entity.EmailVerifications
import com.myfarm.myfarm.domain.emailverifications.port.EmailVerificationsRepository
import com.myfarm.myfarm.domain.users.port.EmailSender
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

@Service
class SendVerificationService(
    private val emailVerificationsRepository: EmailVerificationsRepository,
    private val emailSender: EmailSender,
    private val usersRepository: UsersRepository
) {

    @Transactional
    fun sendVerificationCode(request: SendVerification.Request): SendVerification.Response {
        if (request.verificationType == SendVerification.VerificationType.REGISTRATION) {
            if (usersRepository.existsByEmail(request.email)) {
                throw IllegalArgumentException("이미 가입된 이메일입니다")
            }
        }

        val code = String.format("%06d", Random.nextInt(100000, 999999))
        val expiresAt = LocalDateTime.now().plusMinutes(5)

        val existingVerification = emailVerificationsRepository.findByEmail(request.email)

        val emailVerification = if (existingVerification != null) {
            existingVerification.copy(
                code = code,
                createdAt = LocalDateTime.now(),
                expiresAt = expiresAt,
                verified = false
            )
        } else {
            EmailVerifications(
                id = UUID.randomUUID(),
                email = request.email,
                code = code,
                createdAt = LocalDateTime.now(),
                expiresAt = expiresAt,
                verified = false
            )
        }

        emailVerificationsRepository.save(emailVerification)

        try {
            when (request.verificationType) {
                SendVerification.VerificationType.REGISTRATION -> emailSender.sendVerificationEmail(request.email, code)
                SendVerification.VerificationType.FIND_ID -> emailSender.sendFindIdVerificationEmail(request.email, code)
                SendVerification.VerificationType.FIND_PASSWORD -> emailSender.sendFindPasswordVerificationEmail(request.email, code)
            }
        } catch (e: Exception) {
            emailVerificationsRepository.deleteByEmail(request.email)
            throw RuntimeException("이메일 발송에 실패했습니다", e)
        }

        return SendVerification.Response(
            email = request.email
        )
    }
}
