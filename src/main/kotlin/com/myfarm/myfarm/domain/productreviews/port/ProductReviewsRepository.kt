package com.myfarm.myfarm.domain.productreviews.port

import com.myfarm.myfarm.adapter.out.persistence.productreviews.ProductReviewsJpaRepository
import com.myfarm.myfarm.domain.productreviews.entity.ProductReviews
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductReviewsRepository(
    private val productReviewsJpaRepository: ProductReviewsJpaRepository
) {

    fun findById(id: UUID): ProductReviews? {
        return productReviewsJpaRepository.findById(id).getOrNull()
    }

    fun findByProductId(productId: UUID): List<ProductReviews> {
        return productReviewsJpaRepository.findByProductId(productId)
    }

    fun findByUserId(userId: UUID): List<ProductReviews> {
        return productReviewsJpaRepository.findByUserId(userId)
    }

    fun findByOrderId(orderId: UUID): ProductReviews? {
        return productReviewsJpaRepository.findByOrderId(orderId)
    }

    fun findByOrderIdAndProductId(orderId: UUID, productId: UUID): ProductReviews? {
        return productReviewsJpaRepository.findByOrderIdAndProductId(orderId, productId)
    }

    fun findByProductIdAndUserId(productId: UUID, userId: UUID): List<ProductReviews> {
        return productReviewsJpaRepository.findByProductIdAndUserId(productId, userId)
    }

    fun findByStatus(status: String): List<ProductReviews> {
        return productReviewsJpaRepository.findByStatus(status)
    }

    fun countByProductId(productId: UUID): Long {
        return productReviewsJpaRepository.countByProductId(productId)
    }

    fun findByProductIdAndStatus(productId: UUID, status: String): List<ProductReviews> {
        return productReviewsJpaRepository.findByProductIdAndStatus(productId, status)
    }

    fun save(productReview: ProductReviews): ProductReviews {
        return productReviewsJpaRepository.save(productReview)
    }

    fun saveAll(productReviews: List<ProductReviews>): List<ProductReviews> {
        return productReviewsJpaRepository.saveAll(productReviews)
    }

    fun deleteById(id: UUID) {
        productReviewsJpaRepository.deleteById(id)
    }

    fun deleteAllById(ids: List<UUID>) {
        productReviewsJpaRepository.deleteAllById(ids)
    }
}
