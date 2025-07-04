package com.myfarm.myfarm.adapter.`in`.web.shipments.message

import java.time.LocalDateTime
import java.util.UUID

abstract class ListShipment {

    data class Request(
        val status: String? = null,
        val carrier: String? = null,
        val page: Int = 0,
        val size: Int = 20
    )

    data class Response(
        val totalCount: Int,
        val shipment: List<ShipmentInfo>
    )

    data class ShipmentInfo(
        val id: UUID,
        val orderId: UUID,
        val trackingNumber: String,
        val carrier: String,
        val status: String,
        val shippedAt: LocalDateTime?,
        val deliveredAt: LocalDateTime?,
        val carrierName: String?,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
