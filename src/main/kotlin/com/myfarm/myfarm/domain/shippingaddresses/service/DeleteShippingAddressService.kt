package com.myfarm.myfarm.domain.shippingaddresses.service

import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.DeleteShippingAddress
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.shippingaddresses.port.ShippingAddressesRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteShippingAddressService(
    private val shippingAddressesRepository: ShippingAddressesRepository
) {

    @Transactional
    fun deleteShippingAddress(userId: UUID, id: UUID): DeleteShippingAddress.Response {
        val shippingAddress = shippingAddressesRepository.findById(id)
            .checkOwnership(userId)

        if (shippingAddress.isDefault) {
            throw IllegalArgumentException("기본 배송지는 삭제할 수 없습니다")
        }

        shippingAddressesRepository.deleteById(id)

        return DeleteShippingAddress.Response(
            success = true,
            message = "배송지가 삭제되었습니다"
        )
    }
}
