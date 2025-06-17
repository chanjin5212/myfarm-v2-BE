package com.myfarm.myfarm.adapter.`in`.web.products.message

import com.myfarm.myfarm.domain.products.entity.Products
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

abstract class GetProduct {

    data class PathVariable(val id: UUID)

    data class Response(
        val id: UUID,
        val name: String,
        val description: String?,
        val price: Int,
        val status: Products.Status,
        val sellerId: UUID?,
        val categoryId: UUID,
        val thumbnailUrl: String?,
        val origin: String?,
        val harvestDate: LocalDate?,
        val storageMethod: String?,
        val isOrganic: Boolean,
        val orderCount: Int,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
    )
}
