package com.myfarm.myfarm.adapter.out.external.deliverytracker.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class TrackingResponse(
    val from: From,
    val to: To,
    val state: State,
    val progresses: List<Progress>
)

data class From(
    val name: String?,
    val time: ZonedDateTime?
)

data class To(
    val name: String?,
    val time: ZonedDateTime?
)

data class State(
    val id: String,
    val text: String
)

data class Progress(
    val time: ZonedDateTime,
    val location: Location?,
    val status: Status,
    val description: String?
)

data class Location(
    val name: String?
)

data class Status(
    val id: String,
    val text: String?
)