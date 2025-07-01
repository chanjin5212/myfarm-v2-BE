package com.myfarm.myfarm.domain.productattributes.port

import com.myfarm.myfarm.adapter.out.persistence.productattributes.ProductAttributesJpaRepository
import com.myfarm.myfarm.domain.productattributes.entity.ProductAttributes
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductAttributesRepository(
    private val productAttributesJpaRepository: ProductAttributesJpaRepository
) {

    fun findById(id: UUID): ProductAttributes? {
        return productAttributesJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<ProductAttributes> {
        return productAttributesJpaRepository.findAll()
    }

    fun findByProductId(productId: UUID): List<ProductAttributes> {
        return productAttributesJpaRepository.findByProductId(productId)
    }

    fun findByAttributeName(attributeName: String): List<ProductAttributes> {
        return productAttributesJpaRepository.findByAttributeName(attributeName)
    }

    fun findByProductIdAndAttributeName(productId: UUID, attributeName: String): List<ProductAttributes> {
        return productAttributesJpaRepository.findByProductIdAndAttributeName(productId, attributeName)
    }

    fun save(productAttribute: ProductAttributes): ProductAttributes {
        return productAttributesJpaRepository.save(productAttribute)
    }

    fun saveAll(productAttributes: List<ProductAttributes>): List<ProductAttributes> {
        return productAttributesJpaRepository.saveAll(productAttributes)
    }

    fun deleteById(id: UUID) {
        productAttributesJpaRepository.deleteById(id)
    }

    fun deleteByProductId(productId: UUID) {
        productAttributesJpaRepository.deleteByProductId(productId)
    }

    fun deleteAllById(ids: List<UUID>) {
        productAttributesJpaRepository.deleteAllById(ids)
    }
}
