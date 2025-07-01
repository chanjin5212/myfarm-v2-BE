package com.myfarm.myfarm.adapter.out.persistence.productimages

import com.myfarm.myfarm.domain.productimages.entity.ProductImages
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductImagesJpaRepository : JpaRepository<ProductImages, UUID> {

    fun findByProductId(productId: UUID): List<ProductImages>

    fun findByProductIdOrderBySortOrder(productId: UUID): List<ProductImages>

    fun findByProductIdAndIsThumbnail(productId: UUID, isThumbnail: Boolean): List<ProductImages>

    fun deleteByProductId(productId: UUID)
}
