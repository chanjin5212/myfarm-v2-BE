package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.GetProduct
import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetProductService(
    private val productsRepository: ProductsRepository
) {

    @Transactional(readOnly = true)
    fun getProduct(id: UUID): GetProduct.Response {
        val product = productsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        // 비활성 상품은 조회 불가 (관리자 제외)
        if (product.status != Products.Status.ACTIVE) {
            throw IllegalArgumentException("조회할 수 없는 상품입니다")
        }

        return GetProduct.Response(
            id = product.id,
            name = product.name,
            description = product.description,
            price = product.price,
            status = product.status,
            sellerId = product.sellerId,
            categoryId = product.categoryId,
            thumbnailUrl = product.thumbnailUrl,
            origin = product.origin,
            harvestDate = product.harvestDate,
            storageMethod = product.storageMethod,
            isOrganic = product.isOrganic,
            orderCount = product.orderCount,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )
    }
}
