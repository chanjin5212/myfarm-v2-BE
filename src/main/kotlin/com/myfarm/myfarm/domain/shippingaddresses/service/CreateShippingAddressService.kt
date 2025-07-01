package com.myfarm.myfarm.domain.shippingaddresses.service

import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.CreateShippingAddress
import com.myfarm.myfarm.domain.shippingaddresses.entity.ShippingAddresses
import com.myfarm.myfarm.domain.shippingaddresses.port.ShippingAddressesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateShippingAddressService(
    private val shippingAddressesRepository: ShippingAddressesRepository
) {

    @Transactional
    fun createShippingAddress(userId: UUID, request: CreateShippingAddress.Request): CreateShippingAddress.Response {
        if (request.recipientName.isBlank()) {
            throw IllegalArgumentException("받는 사람 이름은 필수입니다")
        }

        if (request.phone.isBlank()) {
            throw IllegalArgumentException("연락처는 필수입니다")
        }

        if (request.address.isBlank()) {
            throw IllegalArgumentException("주소는 필수입니다")
        }

        if (request.isDefault) {
            val existingDefaultAddress = shippingAddressesRepository.findByUserIdAndIsDefault(userId, true)
            existingDefaultAddress?.let { address ->
                val updatedAddress = address.copy(
                    isDefault = false,
                    updatedAt = LocalDateTime.now()
                )
                shippingAddressesRepository.save(updatedAddress)
            }
        }

        val now = LocalDateTime.now()
        val shippingAddress = ShippingAddresses(
            id = UUID.randomUUID(),
            userId = userId,
            recipientName = request.recipientName,
            phone = request.phone,
            address = request.address,
            detailAddress = request.detailAddress,
            isDefault = request.isDefault,
            memo = request.memo,
            createdAt = now,
            updatedAt = now
        )

        shippingAddressesRepository.save(shippingAddress)

        return CreateShippingAddress.Response(
            success = true,
            message = "배송지가 추가되었습니다"
        )
    }
}