package com.myfarm.myfarm.domain.productimages.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "product_images")
data class ProductImages(
    @Id
    val id: UUID = UUID.randomUUID(),

    val productId: UUID,

    val imageUrl: String,

    val isThumbnail: Boolean = false,

    val sortOrder: Int = 0,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
