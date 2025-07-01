package com.myfarm.myfarm.domain.cartitems.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "cart_items")
data class CartItems(
    @Id
    val id: UUID = UUID.randomUUID(),

    val cartId: UUID,

    val productId: UUID,

    val productOptionId: UUID? = null,

    val quantity: Int = 1,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
