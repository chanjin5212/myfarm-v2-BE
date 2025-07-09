package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.users.message.FindId
import com.myfarm.myfarm.domain.emailverifications.port.EmailVerificationsRepository
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FindIdService(
    private val usersRepository: UsersRepository,
    private val emailVerificationsRepository: EmailVerificationsRepository
) {

    @Transactional
    fun findId(request: FindId.Request): FindId.Response {
        val emailVerification = emailVerificationsRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 인증이 필요합니다")

        if (!emailVerification.verified) {
            throw IllegalArgumentException("이메일 인증이 완료되지 않았습니다")
        }

        val user = usersRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다")

        emailVerificationsRepository.deleteByEmail(request.email)

        return FindId.Response(
            loginId = user.loginId!!
        )
    }
}
