package com.myfarm.myfarm.adapter.`in`.web.products.message

import com.myfarm.myfarm.domain.products.entity.Products
import java.time.LocalDate
import java.util.UUID

abstract class CreateProduct {
    data class Request(
        val name: String,
        val description: String? = null,
        val price: Int,
        val status: Products.Status = Products.Status.ACTIVE,
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
        val imageUrl: String,
        val isThumbnail: Boolean = false,
        val sortOrder: Int = 0
    )

    data class ProductOptionRequest(
        val optionName: String,
        val optionValue: String,
        val additionalPrice: Int = 0,
        val stock: Int = 0,
        val isDefault: Boolean = false
    )

    data class ProductAttributeRequest(
        val attributeName: String,
        val attributeValue: String
    )

    data class Response(
        val success: Boolean,
        val productId: UUID? = null
    )
}
