package com.myfarm.myfarm.domain.productimages.port

import com.myfarm.myfarm.adapter.out.persistence.productimages.ProductImagesJpaRepository
import com.myfarm.myfarm.domain.productimages.entity.ProductImages
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductImagesRepository(
    private val productImagesJpaRepository: ProductImagesJpaRepository
) {

    fun findById(id: UUID): ProductImages? {
        return productImagesJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<ProductImages> {
        return productImagesJpaRepository.findAll()
    }

    fun findByProductId(productId: UUID): List<ProductImages> {
        return productImagesJpaRepository.findByProductId(productId)
    }

    fun findByProductIdOrderBySortOrder(productId: UUID): List<ProductImages> {
        return productImagesJpaRepository.findByProductIdOrderBySortOrder(productId)
    }

    fun findByProductIdAndIsThumbnail(productId: UUID, isThumbnail: Boolean): List<ProductImages> {
        return productImagesJpaRepository.findByProductIdAndIsThumbnail(productId, isThumbnail)
    }

    fun save(productImage: ProductImages): ProductImages {
        return productImagesJpaRepository.save(productImage)
    }

    fun saveAll(productImages: List<ProductImages>): List<ProductImages> {
        return productImagesJpaRepository.saveAll(productImages)
    }

    fun deleteById(id: UUID) {
        productImagesJpaRepository.deleteById(id)
    }

    fun deleteByProductId(productId: UUID) {
        productImagesJpaRepository.deleteByProductId(productId)
    }

    fun deleteAllById(ids: List<UUID>) {
        productImagesJpaRepository.deleteAllById(ids)
    }
}
