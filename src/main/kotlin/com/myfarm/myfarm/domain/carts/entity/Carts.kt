package com.myfarm.myfarm.domain.carts.entity

import com.myfarm.myfarm.domain.common.Authorizable
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "carts")
data class Carts(
    @Id
    val id: UUID = UUID.randomUUID(),

    val userId: UUID,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Authorizable {
    override fun getOwnerId(): UUID = userId
}
