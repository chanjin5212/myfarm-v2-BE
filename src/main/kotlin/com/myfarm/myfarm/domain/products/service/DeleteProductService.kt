package com.myfarm.myfarm.domain.products.service

import com.myfarm.myfarm.adapter.`in`.web.products.message.DeleteProduct
import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class DeleteProductService(
    private val productsRepository: ProductsRepository
) {

    @Transactional
    fun deleteProduct(id: UUID): DeleteProduct.Response {
        val product = productsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다")

        if (product.status == Products.Status.DELETED) {
            throw IllegalArgumentException("이미 삭제된 상품입니다")
        }

        val deletedProduct = product.copy(
            status = Products.Status.DELETED,
            updatedAt = LocalDateTime.now()
        )

        productsRepository.save(deletedProduct)

        return DeleteProduct.Response(success = true)
    }
}
