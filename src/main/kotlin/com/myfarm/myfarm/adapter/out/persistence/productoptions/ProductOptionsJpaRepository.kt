package com.myfarm.myfarm.adapter.out.persistence.productoptions

import com.myfarm.myfarm.domain.productoptions.entity.ProductOptions
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductOptionsJpaRepository : JpaRepository<ProductOptions, UUID> {

    fun findByProductId(productId: UUID): List<ProductOptions>

    fun findByProductIdAndIsDefault(productId: UUID, isDefault: Boolean): List<ProductOptions>

    fun existsByProductIdAndOptionNameAndOptionValue(productId: UUID, optionName: String, optionValue: String): Boolean

    fun deleteByProductId(productId: UUID)
}
