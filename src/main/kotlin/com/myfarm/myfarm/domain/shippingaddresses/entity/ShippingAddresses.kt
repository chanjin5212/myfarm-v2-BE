package com.myfarm.myfarm.domain.shippingaddresses.entity

import com.myfarm.myfarm.domain.common.Authorizable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "shipping_addresses")
data class ShippingAddresses(
    @Id
    val id: UUID = UUID.randomUUID(),

    val userId: UUID,

    val recipientName: String,

    val phone: String,

    val address: String,

    val detailAddress: String? = null,

    val isDefault: Boolean = false,

    val memo: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Authorizable {
    override fun getOwnerId(): UUID = userId
}