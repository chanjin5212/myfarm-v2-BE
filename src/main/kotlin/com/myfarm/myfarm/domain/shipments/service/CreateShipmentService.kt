package com.myfarm.myfarm.domain.shipments.service

import com.myfarm.myfarm.adapter.`in`.web.shipments.message.CreateShipment
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import com.myfarm.myfarm.domain.shipments.entity.Shipments
import com.myfarm.myfarm.domain.shipments.port.ShipmentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateShipmentService(
    private val shipmentsRepository: ShipmentsRepository,
    private val ordersRepository: OrdersRepository
) {

    @Transactional
    fun createShipment(request: CreateShipment.Request): CreateShipment.Response {
        val now = LocalDateTime.now()
        val createdShipments = mutableListOf<Shipments>()

        request.shipments.forEach { shipmentRequest ->
            ordersRepository.findById(shipmentRequest.orderId)
                ?: throw IllegalArgumentException("존재하지 않는 주문입니다: ${shipmentRequest.orderId}")

            if (shipmentsRepository.existsByOrderId(shipmentRequest.orderId)) {
                throw IllegalArgumentException("이미 배송 정보가 등록된 주문입니다: ${shipmentRequest.orderId}")
            }

            if (shipmentsRepository.existsByTrackingNumber(shipmentRequest.trackingNumber)) {
                throw IllegalArgumentException("이미 사용 중인 운송장 번호입니다: ${shipmentRequest.trackingNumber}")
            }

            val shipment = Shipments(
                id = UUID.randomUUID(),
                orderId = shipmentRequest.orderId,
                trackingNumber = shipmentRequest.trackingNumber,
                carrier = shipmentRequest.carrier,
                status = "ready",
                shippedAt = null,
                deliveredAt = null,
                trackingDetails = null,
                lastUpdated = now,
                adminNotes = shipmentRequest.adminNotes,
                carrierName = shipmentRequest.carrierName,
                createdAt = now,
                updatedAt = now
            )

            val savedShipment = shipmentsRepository.save(shipment)
            createdShipments.add(savedShipment)
        }

        return CreateShipment.Response(
            success = true,
            message = "${createdShipments.size}개의 배송 정보가 등록되었습니다"
        )
    }
}
