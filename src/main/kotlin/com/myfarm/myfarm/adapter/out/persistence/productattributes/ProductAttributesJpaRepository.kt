package com.myfarm.myfarm.adapter.out.persistence.productattributes

import com.myfarm.myfarm.domain.productattributes.entity.ProductAttributes
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductAttributesJpaRepository : JpaRepository<ProductAttributes, UUID> {

    fun findByProductId(productId: UUID): List<ProductAttributes>

    fun deleteByProductId(productId: UUID)

    fun findByAttributeName(attributeName: String): List<ProductAttributes>

    fun findByProductIdAndAttributeName(productId: UUID, attributeName: String): List<ProductAttributes>
}
