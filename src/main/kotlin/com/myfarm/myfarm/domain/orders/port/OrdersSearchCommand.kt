package com.myfarm.myfarm.domain.orders.port

import java.time.LocalDateTime
import java.util.UUID

data class OrdersSearchCommand(
    val userId: UUID,
    val status: String? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val sortBy: String = "latest"
)
