package com.myfarm.myfarm.adapter.`in`.web.shipments.message

import java.util.UUID

abstract class DeleteShipment {
    data class Request(
        val shipmentIds: List<UUID>
    )

    data class Response(
        val success: Boolean,
        val message: String? = null
    )
}
