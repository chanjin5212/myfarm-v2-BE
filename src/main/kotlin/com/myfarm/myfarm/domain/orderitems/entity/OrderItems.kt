package com.myfarm.myfarm.domain.orderitems.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "order_items")
data class OrderItems(
    @Id
    val id: UUID = UUID.randomUUID(),

    val orderId: UUID,

    val productId: UUID,

    val productOptionId: UUID,

    val quantity: Int,

    val price: Int,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val options: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
