package com.myfarm.myfarm.adapter.`in`.web.orders.message

import java.util.UUID

abstract class DeleteOrder {
    data class PathVariable(val id: UUID)

    data class Response(
        val success: Boolean
    )
}
