package com.myfarm.myfarm.adapter.out.persistence.shipments

import com.myfarm.myfarm.domain.shipments.entity.Shipments
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ShipmentsJpaRepository : JpaRepository<Shipments, UUID> {

    fun findByOrderId(orderId: UUID): Shipments?

    fun existsByOrderId(orderId: UUID): Boolean

    fun existsByTrackingNumber(trackingNumber: String): Boolean
}
