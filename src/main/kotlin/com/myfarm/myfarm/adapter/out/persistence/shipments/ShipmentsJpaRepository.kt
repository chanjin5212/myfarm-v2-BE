package com.myfarm.myfarm.adapter.out.persistence.shipments

import com.myfarm.myfarm.domain.shipments.entity.Shipments
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ShipmentsJpaRepository : JpaRepository<Shipments, UUID> {

    fun findByOrderId(orderId: UUID): Shipments?

    fun findByTrackingNumber(trackingNumber: String): Shipments?

    fun findByStatus(status: String): List<Shipments>

    fun existsByTrackingNumber(trackingNumber: String): Boolean
}