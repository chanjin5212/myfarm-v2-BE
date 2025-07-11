package com.myfarm.myfarm.domain.productreviews.entity

import com.myfarm.myfarm.domain.common.Authorizable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "product_reviews")
data class ProductReviews(
    @Id
    val id: UUID = UUID.randomUUID(),

    val productId: UUID,

    val userId: UUID,

    val orderId: UUID? = null,

    val rating: Int,

    val content: String,

    val status: String = "active",

    val imageUrl: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Authorizable {
    override fun getOwnerId(): UUID = userId
}
