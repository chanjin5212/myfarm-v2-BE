package com.myfarm.myfarm.adapter.`in`.web.products.message

import java.util.UUID

abstract class DeleteProduct {

    data class PathVariable(val id: UUID)

    data class Response(
        val success: Boolean
    )
}
