package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.users.message.Register
import com.myfarm.myfarm.domain.emailverifications.port.EmailVerificationsRepository
import com.myfarm.myfarm.domain.shippingaddresses.entity.ShippingAddresses
import com.myfarm.myfarm.domain.shippingaddresses.port.ShippingAddressesRepository
import com.myfarm.myfarm.domain.users.entity.Users
import com.myfarm.myfarm.domain.users.port.EmailSender
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class RegisterService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailVerificationsRepository: EmailVerificationsRepository,
    private val emailSender: EmailSender,
    private val shippingAddressesRepository: ShippingAddressesRepository
) {

    @Transactional
    fun register(request: Register.Request): Register.Response {
        val emailVerification = emailVerificationsRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 인증이 필요합니다")

        if (!emailVerification.verified) {
            throw IllegalArgumentException("이메일 인증이 완료되지 않았습니다")
        }

        if (usersRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        if (usersRepository.existsByLoginId(request.loginId)) {
            throw IllegalArgumentException("Login ID already exists")
        }

        val now = LocalDateTime.now()
        val user = Users(
            id = UUID.randomUUID(),
            email = request.email,
            loginId = request.loginId,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            phoneNumber = request.phoneNumber,
            termsAgreed = request.termsAgreed,
            marketingAgreed = request.marketingAgreed,
            createdAt = now,
            updatedAt = now,
            avatarUrl = null,
            googleId = null,
            kakaoId = null,
            naverId = null,
            lastLogin = null,
            nickname = request.nickname,
            postcode = request.postcode,
            address = request.address,
            detailAddress = request.detailAddress,
            isAdmin = false,
            isDeleted = false,
            deletedAt = null
        )

        val savedUser = usersRepository.save(user)

        val defaultShippingAddress = ShippingAddresses(
            id = UUID.randomUUID(),
            userId = savedUser.id,
            recipientName = savedUser.name!!,
            phone = savedUser.phoneNumber!!,
            address = savedUser.address!!,
            detailAddress = savedUser.detailAddress,
            isDefault = true,
            memo = "기본 배송지",
            createdAt = now,
            updatedAt = now
        )

        shippingAddressesRepository.save(defaultShippingAddress)

        emailVerificationsRepository.deleteByEmail(request.email)

        try {
            emailSender.sendWelcomeEmail(savedUser.email)
        } catch (e: Exception) {
            throw RuntimeException("Failed to send registration email", e)
        }

        return Register.Response(
            email = savedUser.email,
            loginId = savedUser.loginId!!
        )
    }
}
