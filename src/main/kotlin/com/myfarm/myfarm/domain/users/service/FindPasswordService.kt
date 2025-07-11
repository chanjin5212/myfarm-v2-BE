package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.users.message.FindPassword
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FindPasswordService(
    private val usersRepository: UsersRepository
) {

    @Transactional
    fun findPassword(request: FindPassword.Request): FindPassword.Response {
        usersRepository.findByEmailAndLoginId(request.email, request.loginId)
            ?: return FindPassword.Response(available = false)

        return FindPassword.Response(available = true)
    }
}
