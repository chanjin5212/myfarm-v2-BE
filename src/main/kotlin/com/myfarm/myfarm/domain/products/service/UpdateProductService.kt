package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.UpdateProduct
import com.myfarm.myfarm.domain.categories.port.CategoriesRepository
import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateProductService(
    private val productsRepository: ProductsRepository,
    private val categoriesRepository: CategoriesRepository
) {

    @Transactional
    fun updateProduct(id: UUID, request: UpdateProduct.Request): UpdateProduct.Response {
        val product = productsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        request.categoryId?.let { categoryId ->
            categoriesRepository.findById(categoryId)
                ?: throw IllegalArgumentException("존재하지 않는 카테고리입니다")
        }

        request.price?.let { price ->
            if (price <= 0) {
                throw IllegalArgumentException("상품 가격은 0보다 커야 합니다")
            }
        }

        request.name?.let { newName ->
            if (newName != product.name) {
                val existingProducts = productsRepository.findByStatusAndSellerId(Products.Status.ACTIVE, product.sellerId!!)
                if (existingProducts.any { it.id != id && it.name.equals(newName, ignoreCase = true) }) {
                    throw IllegalArgumentException("이미 등록된 상품명입니다")
                }
            }
        }

        val updatedProduct = product.copy(
            name = request.name ?: product.name,
            description = request.description ?: product.description,
            price = request.price ?: product.price,
            status = request.status ?: product.status,
            categoryId = request.categoryId ?: product.categoryId,
            thumbnailUrl = request.thumbnailUrl ?: product.thumbnailUrl,
            origin = request.origin ?: product.origin,
            harvestDate = request.harvestDate ?: product.harvestDate,
            storageMethod = request.storageMethod ?: product.storageMethod,
            isOrganic = request.isOrganic ?: product.isOrganic,
            updatedAt = LocalDateTime.now()
        )

        productsRepository.save(updatedProduct)

        return UpdateProduct.Response(success = true)
    }
}
