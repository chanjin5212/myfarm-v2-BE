package com.myfarm.myfarm.domain.products.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = "products")
data class Products(
    @Id
    val id: UUID = UUID.randomUUID(),

    val name: String,

    val description: String? = null,

    val price: Int,

    @Enumerated(EnumType.STRING)
    val status: Status = Status.ACTIVE,

    val sellerId: UUID?,

    val categoryId: UUID,

    val thumbnailUrl: String? = null,

    val origin: String? = null,

    val harvestDate: LocalDate? = null,

    val storageMethod: String? = null,

    val isOrganic: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    enum class Status(val value: String, val description: String) {
        ACTIVE("active", "판매중"),
        INACTIVE("inactive", "판매중지"),
        SOLDOUT("soldout", "품절"),
        DELETED("deleted", "삭제됨");
    }
}
