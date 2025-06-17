package com.myfarm.myfarm.adapter.`in`.web.products.message

import com.myfarm.myfarm.domain.products.entity.Products
import java.time.LocalDate
import java.util.UUID

abstract class UpdateProduct {

    data class PathVariable(val id: UUID)

    data class Request(
        val name: String? = null,
        val description: String? = null,
        val price: Int? = null,
        val status: Products.Status? = null,
        val categoryId: UUID? = null,
        val thumbnailUrl: String? = null,
        val origin: String? = null,
        val harvestDate: LocalDate? = null,
        val storageMethod: String? = null,
        val isOrganic: Boolean? = null
    )

    data class Response(
        val success: Boolean
    )
}
