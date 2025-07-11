package com.myfarm.myfarm.adapter.out.persistence.productreviews

import com.myfarm.myfarm.domain.productreviews.entity.ProductReviews
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductReviewsJpaRepository : JpaRepository<ProductReviews, UUID> {

    fun findByProductId(productId: UUID): List<ProductReviews>

    fun findByUserId(userId: UUID): List<ProductReviews>

    fun findByOrderId(orderId: UUID): ProductReviews?

    fun findByOrderIdAndProductId(orderId: UUID, productId: UUID): ProductReviews?

    fun findByProductIdAndUserId(productId: UUID, userId: UUID): List<ProductReviews>

    fun findByStatus(status: String): List<ProductReviews>

    fun countByProductId(productId: UUID): Long

    fun findByProductIdAndStatus(productId: UUID, status: String): List<ProductReviews>
}
