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

    fun existsByOrderId(orderId: UUID): Boolean {
        return shipmentsJpaRepository.existsByOrderId(orderId)
    }

    fun existsByTrackingNumber(trackingNumber: String): Boolean {
        return shipmentsJpaRepository.existsByTrackingNumber(trackingNumber)
    }

    fun save(shipment: Shipments): Shipments {
        return shipmentsJpaRepository.save(shipment)
    }

    fun saveAll(shipments: List<Shipments>): List<Shipments> {
        return shipmentsJpaRepository.saveAll(shipments)
    }

    fun deleteById(id: UUID) {
        shipmentsJpaRepository.deleteById(id)
    }

    fun deleteAllById(ids: List<UUID>) {
        shipmentsJpaRepository.deleteAllById(ids)
    }
}
