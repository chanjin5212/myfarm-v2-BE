package com.myfarm.myfarm.adapter.`in`.web.shipments.message

import java.util.UUID

abstract class CreateShipment {
    data class Request(
        val shipments: List<ShipmentRequest>
    )

    data class ShipmentRequest(
        val orderId: UUID,
        val trackingNumber: String,
        val carrier: String,
        val carrierName: String? = null,
        val adminNotes: String? = null
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
