package com.myfarm.myfarm.adapter.out.persistence.producttags

import com.myfarm.myfarm.domain.producttags.entity.ProductTags
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductTagsJpaRepository : JpaRepository<ProductTags, UUID> {

    fun findByProductId(productId: UUID): List<ProductTags>

    fun deleteByProductId(productId: UUID)

    fun findByTag(tag: String): List<ProductTags>

    fun findByProductIdAndTag(productId: UUID, tag: String): ProductTags?
}
