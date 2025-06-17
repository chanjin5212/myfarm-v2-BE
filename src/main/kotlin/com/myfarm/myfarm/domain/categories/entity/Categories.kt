package com.myfarm.myfarm.domain.categories.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "categories")
data class Categories(
    @Id
    val id: UUID = UUID.randomUUID(),

    val name: String,

    val parentId: UUID? = null,

    val description: String? = null,

    val isActive: Boolean = true,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
)