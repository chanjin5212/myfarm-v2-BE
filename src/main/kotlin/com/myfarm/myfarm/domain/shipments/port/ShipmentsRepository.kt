package com.myfarm.myfarm.domain.shipments.port

import com.myfarm.myfarm.adapter.out.persistence.shipments.ShipmentsJpaRepository
import com.myfarm.myfarm.domain.shipments.entity.Shipments
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ShipmentsRepository(
    private val shipmentsJpaRepository: ShipmentsJpaRepository
) {

    fun findById(id: UUID): Shipments? {
        return shipmentsJpaRepository.findById(id).getOrNull()
    }

    fun findByOrderId(orderId: UUID): Shipments? {
        return shipmentsJpaRepository.findByOrderId(orderId)
    }

    fun findByTrackingNumber(trackingNumber: String): Shipments? {
        return shipmentsJpaRepository.findByTrackingNumber(trackingNumber)
    }

    fun findByStatus(status: String): List<Shipments> {
        return shipmentsJpaRepository.findByStatus(status)
    }

    fun existsByTrackingNumber(trackingNumber: String): Boolean {
        return shipmentsJpaRepository.existsByTrackingNumber(trackingNumber)
    }

    fun save(shipment: Shipments): Shipments {
        return shipmentsJpaRepository.save(shipment)
    }

    fun deleteById(id: UUID) {
        shipmentsJpaRepository.deleteById(id)
    }
}