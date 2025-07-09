package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.users.message.Me
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MeService(
    private val usersRepository: UsersRepository
) {

    fun getCurrentUser(userId: UUID): Me.Response {
        val user = usersRepository.findById(userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다")

        return Me.Response(
            id = user.id,
            email = user.email,
            loginId = user.loginId!!,
            name = user.name!!,
            nickname = user.nickname,
            phoneNumber = user.phoneNumber!!,
            postcode = user.postcode,
            address = user.address,
            detailAddress = user.detailAddress,
            avatarUrl = user.avatarUrl,
            termsAgreed = user.termsAgreed,
            marketingAgreed = user.marketingAgreed,
            createdAt = user.createdAt,
            lastLogin = user.lastLogin
        )
    }
}