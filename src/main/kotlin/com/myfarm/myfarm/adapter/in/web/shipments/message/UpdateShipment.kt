package com.myfarm.myfarm.adapter.`in`.web.shipments.message

import java.time.LocalDateTime
import java.util.UUID

abstract class UpdateShipment {
    data class Request(
        val shipments: List<ShipmentUpdateRequest>
    )

    data class ShipmentUpdateRequest(
        val id: UUID,
        val status: String? = null,
        val shippedAt: LocalDateTime? = null,
        val deliveredAt: LocalDateTime? = null,
        val trackingDetails: String? = null,
        val adminNotes: String? = null,
        val carrierName: String? = null
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
