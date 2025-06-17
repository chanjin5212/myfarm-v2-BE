package com.myfarm.myfarm.domain.products.port

import com.myfarm.myfarm.domain.products.entity.Products
import java.util.UUID

data class ProductsSearchCommand(
    val status: Products.Status = Products.Status.ACTIVE,
    val categoryId: UUID? = null,
    val keyword: String? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val sortBy: String = "latest"
)
