package com.myfarm.myfarm.adapter.`in`.web.products.message

import java.time.LocalDate
import java.util.UUID

abstract class CreateProduct {
    data class Request(
        val name: String,
        val description: String? = null,
        val price: Int,
        val categoryId: UUID,
        val thumbnailUrl: String? = null,
        val origin: String? = null,
        val harvestDate: LocalDate? = null,
        val storageMethod: String? = null,
        val isOrganic: Boolean = false
    )

    data class Response(
        val success: Boolean,
        val productId: UUID? = null
    )
}
