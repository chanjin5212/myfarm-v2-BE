package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2.OAuth2UserInfo
import com.myfarm.myfarm.domain.users.entity.Users
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class OAuth2UserService(
    private val usersRepository: UsersRepository
) {

    @Transactional
    fun processOAuth2User(registrationId: String, userInfo: OAuth2UserInfo): Users {
        val existingUser = usersRepository.findByEmail(userInfo.email)

        return if (existingUser != null) {
            updateExistingUser(existingUser, registrationId, userInfo)
        } else {
            createNewOAuth2User(registrationId, userInfo)
        }
    }

    private fun createNewOAuth2User(registrationId: String, userInfo: OAuth2UserInfo): Users {
        val now = LocalDateTime.now()

        val newUser = Users(
            id = UUID.randomUUID(),
            email = userInfo.email,
            loginId = null,
            password = "",
            name = userInfo.name,
            phoneNumber = userInfo.phoneNumber,
            termsAgreed = true,
            marketingAgreed = false,
            createdAt = now,
            updatedAt = now,
            avatarUrl = userInfo.imageUrl,
            googleId = null,
            kakaoId = if (registrationId == "kakao") userInfo.id else null,
            naverId = if (registrationId == "naver") userInfo.id else null,
            lastLogin = now,
            nickname = userInfo.name,
            postcode = null,
            address = null,
            detailAddress = null,
            isAdmin = false,
            isDeleted = false,
            deletedAt = null
        )

        return usersRepository.save(newUser)
    }

    private fun updateExistingUser(existingUser: Users, registrationId: String, userInfo: OAuth2UserInfo): Users {
        val updatedUser = existingUser.copy(
            name = userInfo.name,
            phoneNumber = userInfo.phoneNumber ?: existingUser.phoneNumber,
            avatarUrl = userInfo.imageUrl ?: existingUser.avatarUrl,
            kakaoId = if (registrationId == "kakao") userInfo.id else existingUser.kakaoId,
            naverId = if (registrationId == "naver") userInfo.id else existingUser.naverId,
            lastLogin = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return usersRepository.save(updatedUser)
    }
}
