package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.users.message.ResetPassword
import com.myfarm.myfarm.domain.emailverifications.port.EmailVerificationsRepository
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ResetPasswordService(
    private val usersRepository: UsersRepository,
    private val emailVerificationsRepository: EmailVerificationsRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun resetPassword(request: ResetPassword.Request): ResetPassword.Response {
        val emailVerification = emailVerificationsRepository.findByEmail(request.email)
            ?: return ResetPassword.Response(success = false, message = "이메일 인증이 필요합니다")

        if (!emailVerification.verified) {
            return ResetPassword.Response(success = false, message = "이메일 인증이 완료되지 않았습니다")
        }

        val user = usersRepository.findByEmailAndLoginId(request.email, request.loginId)
            ?: return ResetPassword.Response(success = false, message = "사용자 정보가 일치하지 않습니다")

        val updatedUser = user.copy(
            password = passwordEncoder.encode(request.newPassword),
            updatedAt = LocalDateTime.now()
        )

        usersRepository.save(updatedUser)

        emailVerificationsRepository.deleteByEmail(request.email)

        return ResetPassword.Response(success = true)
    }
}
