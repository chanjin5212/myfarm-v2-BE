package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.ListProduct
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListProductService(
    private val productsRepository: ProductsRepository,
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional(readOnly = true)
    fun listProducts(request: ListProduct.Request): ListProduct.Response {
        val pageSize = when {
            request.size > 100 -> 100
            request.size <= 0 -> 20
            else -> request.size
        }

        request.categoryId?.let { categoryId ->
            categoriesRepository.findById(categoryId)
                ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")
        }

        if (request.minPrice != null && request.maxPrice != null) {
            if (request.minPrice > request.maxPrice) {
                throw IllegalArgumentException("최소 가격이 최대 가격보다 클 수 없습니다")
            }
        }

        val validSortOptions = listOf("latest", "oldest", "popular", "priceAsc", "priceDesc", "name")
        val sortBy = if (request.sortBy in validSortOptions) request.sortBy else "latest"

        val productsPage = productsRepository.findByFilters(
            status = Products.Status.ACTIVE,
            categoryId = request.categoryId,
            keyword = request.keyword?.trim()?.takeIf { it.isNotBlank() },
            minPrice = request.minPrice,
            maxPrice = request.maxPrice,
            sortBy = sortBy,
            page = request.page,
            size = pageSize
        )

        val productSummaries = productsPage.content.map { product ->
            ListProduct.ProductSummary(
                id = product.id,
                name = product.name,
                price = product.price,
                thumbnailUrl = product.thumbnailUrl,
                orderCount = product.orderCount,
                createdAt = product.createdAt
            )
        }

        return ListProduct.Response(
            totalCount = productsPage.totalElements.toInt(),
            products = productSummaries
        )
    }
}
