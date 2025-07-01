package com.myfarm.myfarm.domain.productoptions.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "product_options")
data class ProductOptions(
    @Id
    val id: UUID = UUID.randomUUID(),

    val productId: UUID,

    val optionName: String,

    val optionValue: String,

    val additionalPrice: Int = 0,

    val stock: Int = 0,

    val isDefault: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
