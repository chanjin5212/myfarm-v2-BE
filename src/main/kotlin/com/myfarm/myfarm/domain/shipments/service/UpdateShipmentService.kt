package com.myfarm.myfarm.domain.shipments.service

import com.myfarm.myfarm.adapter.`in`.web.shipments.message.UpdateShipment
import com.myfarm.myfarm.domain.shipments.port.ShipmentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UpdateShipmentService(
    private val shipmentsRepository: ShipmentsRepository
) {

    @Transactional
    fun updateShipment(request: UpdateShipment.Request): UpdateShipment.Response {
        val now = LocalDateTime.now()
        val updatedShipments = mutableListOf<String>()

        request.shipments.forEach { shipmentRequest ->
            val existingShipment = shipmentsRepository.findById(shipmentRequest.id)
                ?: throw IllegalArgumentException("존재하지 않는 배송 정보입니다: ${shipmentRequest.id}")

            val updatedShipment = existingShipment.copy(
                status = shipmentRequest.status ?: existingShipment.status,
                shippedAt = shipmentRequest.shippedAt ?: existingShipment.shippedAt,
                deliveredAt = shipmentRequest.deliveredAt ?: existingShipment.deliveredAt,
                trackingDetails = shipmentRequest.trackingDetails ?: existingShipment.trackingDetails,
                adminNotes = shipmentRequest.adminNotes ?: existingShipment.adminNotes,
                carrierName = shipmentRequest.carrierName ?: existingShipment.carrierName,
                lastUpdated = now,
                updatedAt = now
            )

            shipmentsRepository.save(updatedShipment)
            updatedShipments.add(updatedShipment.trackingNumber)
        }

        return UpdateShipment.Response(
            success = true,
            message = "${updatedShipments.size}개의 배송 정보가 수정되었습니다"
        )
    }
}
