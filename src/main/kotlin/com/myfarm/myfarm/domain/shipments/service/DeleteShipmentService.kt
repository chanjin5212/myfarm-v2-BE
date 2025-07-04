package com.myfarm.myfarm.domain.shipments.service

import com.myfarm.myfarm.adapter.`in`.web.shipments.message.DeleteShipment
import com.myfarm.myfarm.domain.shipments.port.ShipmentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteShipmentService(
    private val shipmentsRepository: ShipmentsRepository
) {

    @Transactional
    fun deleteShipment(request: DeleteShipment.Request): DeleteShipment.Response {
        val existingShipments = mutableListOf<String>()

        request.shipmentIds.forEach { shipmentId ->
            val shipment = shipmentsRepository.findById(shipmentId)
                ?: throw IllegalArgumentException("존재하지 않는 배송 정보입니다: $shipmentId")

            existingShipments.add(shipment.trackingNumber)
        }

        shipmentsRepository.deleteAllById(request.shipmentIds)

        return DeleteShipment.Response(
            success = true,
            message = "${existingShipments.size}개의 배송 정보가 삭제되었습니다"
        )
    }
}
