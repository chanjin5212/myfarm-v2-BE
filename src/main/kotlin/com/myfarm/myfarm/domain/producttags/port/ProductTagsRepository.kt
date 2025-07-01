package com.myfarm.myfarm.domain.producttags.port

import com.myfarm.myfarm.adapter.out.persistence.producttags.ProductTagsJpaRepository
import com.myfarm.myfarm.domain.producttags.entity.ProductTags
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductTagsRepository(
    private val productTagsJpaRepository: ProductTagsJpaRepository
) {

    fun findById(id: UUID): ProductTags? {
        return productTagsJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<ProductTags> {
        return productTagsJpaRepository.findAll()
    }

    fun findByProductId(productId: UUID): List<ProductTags> {
        return productTagsJpaRepository.findByProductId(productId)
    }

    fun findByTag(tag: String): List<ProductTags> {
        return productTagsJpaRepository.findByTag(tag)
    }

    fun findByProductIdAndTag(productId: UUID, tag: String): ProductTags? {
        return productTagsJpaRepository.findByProductIdAndTag(productId, tag)
    }

    fun save(productTag: ProductTags): ProductTags {
        return productTagsJpaRepository.save(productTag)
    }

    fun saveAll(productTags: List<ProductTags>): List<ProductTags> {
        return productTagsJpaRepository.saveAll(productTags)
    }

    fun deleteById(id: UUID) {
        productTagsJpaRepository.deleteById(id)
    }

    fun deleteByProductId(productId: UUID) {
        productTagsJpaRepository.deleteByProductId(productId)
    }

    fun deleteByIds(ids: List<UUID>) {
        productTagsJpaRepository.deleteAllById(ids)
    }
}
