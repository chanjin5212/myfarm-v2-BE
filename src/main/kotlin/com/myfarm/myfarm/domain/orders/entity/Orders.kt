package com.myfarm.myfarm.domain.orders.entity

import com.myfarm.myfarm.domain.common.Authorizable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "orders")
data class Orders(
    @Id
    val id: UUID = UUID.randomUUID(),

    val orderNumber: String? = null,

    val userId: UUID,

    val status: String = "pending",

    val shippingName: String,

    val shippingPhone: String,

    val shippingAddress: String,

    val shippingDetailAddress: String? = null,

    val shippingMemo: String? = null,

    val paymentMethod: String,

    val totalAmount: Int,

    val tid: String? = null,

    val cancelReason: String? = null,

    val cancelDate: LocalDateTime? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Authorizable {
    override fun getOwnerId(): UUID = userId
}
