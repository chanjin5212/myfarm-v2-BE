package com.myfarm.myfarm.adapter.`in`.web.shipments.message

import java.time.LocalDateTime
import java.util.UUID

abstract class GetShipment {

    data class PathVariable(val id: UUID)

    data class Response(
        val id: UUID,
        val orderId: UUID,
        val trackingNumber: String,
        val carrier: String,
        val status: String,
        val shippedAt: LocalDateTime?,
        val deliveredAt: LocalDateTime?,
        val trackingDetails: String?,
        val lastUpdated: LocalDateTime?,
        val adminNotes: String?,
        val carrierName: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
