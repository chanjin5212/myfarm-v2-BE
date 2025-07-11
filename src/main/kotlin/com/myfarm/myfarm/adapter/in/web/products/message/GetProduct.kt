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
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val images: List<ProductImageInfo>,
        val options: List<ProductOptionInfo>,
        val attributes: List<ProductAttributeInfo>,
        val tags: List<String>
    )

    data class ProductImageInfo(
        val id: UUID,
        val imageUrl: String,
        val isThumbnail: Boolean,
        val sortOrder: Int
    )

    data class ProductOptionInfo(
        val id: UUID,
        val optionName: String,
        val optionValue: String,
        val additionalPrice: Int,
        val stock: Int,
        val isDefault: Boolean
    )

    data class ProductAttributeInfo(
        val id: UUID,
        val attributeName: String,
        val attributeValue: String
    )
}
