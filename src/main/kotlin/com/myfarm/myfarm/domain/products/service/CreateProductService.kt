package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.CreateProduct
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateProductService(
    private val productsRepository: ProductsRepository,
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional
    fun createProduct(request: CreateProduct.Request, sellerId: UUID): CreateProduct.Response {
        categoriesRepository.findById(request.categoryId)
            ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")

        val existingProducts = productsRepository.findByStatusAndSellerId(Products.Status.ACTIVE, sellerId)
        if (existingProducts.any { it.name.equals(request.name, ignoreCase = true) }) {
            throw IllegalArgumentException("이미 등록된 상품명입니다")
        }

        if (request.price <= 0) {
            throw IllegalArgumentException("상품 가격은 0보다 커야 합니다")
        }

        val now = LocalDateTime.now()
        val product = Products(
            id = UUID.randomUUID(),
            name = request.name,
            description = request.description,
            price = request.price,
            status = Products.Status.ACTIVE,
            sellerId = sellerId,
            categoryId = request.categoryId,
            thumbnailUrl = request.thumbnailUrl,
            origin = request.origin,
            harvestDate = request.harvestDate,
            storageMethod = request.storageMethod,
            isOrganic = request.isOrganic,
            orderCount = 0,
            createdAt = now,
            updatedAt = now
        )

        val savedProduct = productsRepository.save(product)

        return CreateProduct.Response(
            success = true,
            productId = savedProduct.id
        )
    }
}
