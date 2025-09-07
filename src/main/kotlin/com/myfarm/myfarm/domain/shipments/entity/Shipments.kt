package com.myfarm.myfarm.domain.shipments.entity

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
@Table(name = "shipments")
data class Shipments(
    @Id
    val id: UUID = UUID.randomUUID(),

    val orderId: UUID,

    val trackingNumber: String,

    val carrier: String,

    val status: String = "ready",

    val shippedAt: LocalDateTime? = null,

    val deliveredAt: LocalDateTime? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val trackingDetails: String? = null,

    val lastUpdated: LocalDateTime? = null,

    val adminNotes: String? = null,

    val carrierName: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)
