package com.myfarm.myfarm.domain.productattributes.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "product_attributes")
data class ProductAttributes(
    @Id
    val id: UUID = UUID.randomUUID(),

    val productId: UUID,

    val attributeName: String,

    val attributeValue: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
