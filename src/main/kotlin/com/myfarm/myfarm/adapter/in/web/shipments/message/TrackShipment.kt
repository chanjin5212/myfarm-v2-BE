package com.myfarm.myfarm.adapter.`in`.web.shipments.message

import com.myfarm.myfarm.adapter.out.external.deliverytracker.message.Progress
import com.myfarm.myfarm.adapter.out.external.deliverytracker.message.State
import java.time.ZonedDateTime
import java.util.UUID

class TrackShipment {
    data class Request(
        val shipmentId: UUID
    )

    data class Response(
        val success: Boolean,
        val shipmentId: UUID,
        val trackingNumber: String,
        val carrier: String,
        val state: State,
        val progresses: List<Progress>,
        val from: TrackingFrom?,
        val to: TrackingTo?
    )

    data class TrackingFrom(
        val name: String?,
        val time: ZonedDateTime?
    )

    data class TrackingTo(
        val name: String?,
        val time: ZonedDateTime?
    )
}