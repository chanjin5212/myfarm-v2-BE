package com.myfarm.myfarm.domain.payments.entity

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
@Table(name = "payments")
data class Payments(
    @Id
    val id: UUID = UUID.randomUUID(),

    val orderId: UUID,

    val paymentKey: String,

    val paymentMethod: String,

    val paymentProvider: String,

    val amount: Int,

    val status: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val paymentDetails: Any? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
