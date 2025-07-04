package com.myfarm.myfarm.domain.shipments.service

import com.myfarm.myfarm.adapter.`in`.web.shipments.message.GetShipment
import com.myfarm.myfarm.domain.shipments.port.ShipmentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetShipmentService(
    private val shipmentsRepository: ShipmentsRepository
) {

    @Transactional(readOnly = true)
    fun getShipment(id: UUID): GetShipment.Response {
        val shipment = shipmentsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 배송 정보입니다")

        return GetShipment.Response(
            id = shipment.id,
            orderId = shipment.orderId,
            trackingNumber = shipment.trackingNumber,
            carrier = shipment.carrier,
            status = shipment.status,
            shippedAt = shipment.shippedAt,
            deliveredAt = shipment.deliveredAt,
            trackingDetails = shipment.trackingDetails,
            lastUpdated = shipment.lastUpdated,
            adminNotes = shipment.adminNotes,
            carrierName = shipment.carrierName,
            createdAt = shipment.createdAt,
            updatedAt = shipment.updatedAt
        )
    }
}
