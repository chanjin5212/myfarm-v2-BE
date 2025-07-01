package com.myfarm.myfarm.domain.shippingaddresses.service

import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.ListShippingAddress
import com.myfarm.myfarm.domain.shippingaddresses.port.ShippingAddressesRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ListShippingAddressService(
    private val shippingAddressesRepository: ShippingAddressesRepository
) {

    @Transactional(readOnly = true)
    fun listShippingAddress(userId: UUID, request: ListShippingAddress.Request): ListShippingAddress.Response {
        val pageable = PageRequest.of(request.page, request.size)
        val addressPage = shippingAddressesRepository.findByUserId(userId, pageable)

        val addressInfos = addressPage.content.map { address ->
            ListShippingAddress.AddressInfo(
                id = address.id,
                recipientName = address.recipientName,
                phone = address.phone,
                address = address.address,
                detailAddress = address.detailAddress,
                isDefault = address.isDefault,
                memo = address.memo,
                createdAt = address.createdAt,
                updatedAt = address.updatedAt
            )
        }

        return ListShippingAddress.Response(
            totalCount = addressPage.totalElements.toInt(),
            address = addressInfos
        )
    }
}