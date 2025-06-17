package com.myfarm.myfarm.adapter.`in`.web.products.message

import java.time.LocalDateTime
import java.util.UUID

abstract class ListProduct {

    data class Request(
        val categoryId: UUID? = null,
        val keyword: String? = null,
        val minPrice: Int? = null,
        val maxPrice: Int? = null,
        val sortBy: String = "latest",
        val page: Int = 0,
        val size: Int = 20
    )

    data class Response(
        val totalCount: Int,
        val products: List<ProductSummary>
    )

    data class ProductSummary(
        val id: UUID,
        val name: String,
        val price: Int,
        val thumbnailUrl: String?,
        val orderCount: Int,
        val createdAt: LocalDateTime
    )
}
