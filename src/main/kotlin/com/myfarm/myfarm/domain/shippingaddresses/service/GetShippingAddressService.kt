package com.myfarm.myfarm.domain.shippingaddresses.service

import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.GetShippingAddress
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.shippingaddresses.port.ShippingAddressesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetShippingAddressService(
    private val shippingAddressesRepository: ShippingAddressesRepository
) {

    @Transactional(readOnly = true)
    fun getShippingAddress(userId: UUID, id: UUID): GetShippingAddress.Response {
        val shippingAddress = shippingAddressesRepository.findById(id)
            .checkOwnership(userId)

        return GetShippingAddress.Response(
            id = shippingAddress.id,
            userId = shippingAddress.userId,
            recipientName = shippingAddress.recipientName,
            phone = shippingAddress.phone,
            address = shippingAddress.address,
            detailAddress = shippingAddress.detailAddress,
            isDefault = shippingAddress.isDefault,
            memo = shippingAddress.memo,
            createdAt = shippingAddress.createdAt,
            updatedAt = shippingAddress.updatedAt
        )
    }
}
