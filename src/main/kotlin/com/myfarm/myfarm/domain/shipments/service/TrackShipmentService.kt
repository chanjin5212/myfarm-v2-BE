package com.myfarm.myfarm.domain.shipments.service

import com.myfarm.myfarm.adapter.out.external.deliverytracker.DeliveryTrackerClient
import com.myfarm.myfarm.adapter.out.external.deliverytracker.message.TrackingResponse
import com.myfarm.myfarm.domain.shipments.entity.Shipments
import com.myfarm.myfarm.domain.shipments.port.ShipmentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class TrackShipmentService(
    private val shipmentsRepository: ShipmentsRepository,
    private val deliveryTrackerClient: DeliveryTrackerClient
) {

    fun trackShipment(shipmentId: UUID): Mono<TrackingResponse> {
        val shipment = shipmentsRepository.findById(shipmentId)
            ?: throw IllegalArgumentException("배송 정보를 찾을 수 없습니다")

        val carrierId = shipment.carrier

        return deliveryTrackerClient.getTrackingInfo(carrierId, shipment.trackingNumber)
    }

    @Transactional
    fun updateShipmentStatus(shipmentId: UUID): Mono<Shipments> {
        val shipment = shipmentsRepository.findById(shipmentId)
            ?: throw IllegalArgumentException("배송 정보를 찾을 수 없습니다")

        val carrierId = shipment.carrier

        return deliveryTrackerClient.getTrackingInfo(carrierId, shipment.trackingNumber)
            .map { trackingResponse ->
                val updatedShipment = shipment.copy(
                    status = trackingResponse.state.text,
                    deliveredAt = trackingResponse.to.time?.toLocalDateTime(),
                    lastUpdated = java.time.LocalDateTime.now(),
                    trackingDetails = convertTrackingToJson(trackingResponse)
                )
                shipmentsRepository.save(updatedShipment)
            }
    }

    private fun convertTrackingToJson(trackingResponse: TrackingResponse): String {
        return com.fasterxml.jackson.module.kotlin.jacksonObjectMapper()
            .writeValueAsString(trackingResponse)
    }
}