package com.myfarm.myfarm.domain.shippingaddresses.service

import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.UpdateShippingAddress
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.shippingaddresses.port.ShippingAddressesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateShippingAddressService(
    private val shippingAddressesRepository: ShippingAddressesRepository
) {

    @Transactional
    fun updateShippingAddress(userId: UUID, id: UUID, request: UpdateShippingAddress.Request): UpdateShippingAddress.Response {
        val existingAddress = shippingAddressesRepository.findById(id)
            .checkOwnership(userId)

        if (request.recipientName.isBlank()) {
            throw IllegalArgumentException("받는 사람 이름은 필수입니다")
        }

        if (request.phone.isBlank()) {
            throw IllegalArgumentException("연락처는 필수입니다")
        }

        if (request.address.isBlank()) {
            throw IllegalArgumentException("주소는 필수입니다")
        }

        // 기본 배송지로 설정하려는 경우, 기존 기본 배송지를 해제
        if (request.isDefault && !existingAddress.isDefault) {
            val currentDefaultAddress = shippingAddressesRepository.findByUserIdAndIsDefault(userId, true)
            currentDefaultAddress?.let { address ->
                val updatedAddress = address.copy(
                    isDefault = false,
                    updatedAt = LocalDateTime.now()
                )
                shippingAddressesRepository.save(updatedAddress)
            }
        }

        val updatedAddress = existingAddress.copy(
            recipientName = request.recipientName,
            phone = request.phone,
            address = request.address,
            detailAddress = request.detailAddress,
            isDefault = request.isDefault,
            memo = request.memo,
            updatedAt = LocalDateTime.now()
        )

        shippingAddressesRepository.save(updatedAddress)

        return UpdateShippingAddress.Response(
            success = true,
            message = "배송지가 수정되었습니다"
        )
    }
}
