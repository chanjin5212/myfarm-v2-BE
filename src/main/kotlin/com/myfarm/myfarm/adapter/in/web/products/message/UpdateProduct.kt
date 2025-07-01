package com.myfarm.myfarm.adapter.`in`.web.products.message

import com.myfarm.myfarm.domain.products.entity.Products
import java.time.LocalDate
import java.util.UUID

abstract class UpdateProduct {
    data class PathVariable(val id: UUID)

    data class Request(
        val name: String,
        val description: String? = null,
        val price: Int,
        val status: Products.Status,
        val categoryId: UUID,
        val origin: String? = null,
        val harvestDate: LocalDate? = null,
        val storageMethod: String? = null,
        val isOrganic: Boolean = false,
        val images: List<ProductImageRequest> = emptyList(),
        val options: List<ProductOptionRequest> = emptyList(),
        val attributes: List<ProductAttributeRequest> = emptyList(),
        val tags: List<String> = emptyList()
    )

    data class ProductImageRequest(
        val id: UUID? = null,
        val imageUrl: String,
        val isThumbnail: Boolean = false,
        val sortOrder: Int = 0
    )

    data class ProductOptionRequest(
        val id: UUID? = null,
        val optionName: String,
        val optionValue: String,
        val additionalPrice: Int = 0,
        val stock: Int = 0,
        val isDefault: Boolean = false
    )

    data class ProductAttributeRequest(
        val id: UUID? = null,
        val attributeName: String,
        val attributeValue: String
    )

    data class Response(
        val success: Boolean
    )
}
