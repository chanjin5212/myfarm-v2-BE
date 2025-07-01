package com.myfarm.myfarm.domain.producttags.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "product_tags")
data class ProductTags(
    @Id
    val id: UUID = UUID.randomUUID(),

    val productId: UUID,

    val tag: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
