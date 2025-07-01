package com.myfarm.myfarm.domain.productoptions.port

import com.myfarm.myfarm.adapter.out.persistence.productoptions.ProductOptionsJpaRepository
import com.myfarm.myfarm.domain.productoptions.entity.ProductOptions
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductOptionsRepository(
    private val productOptionsJpaRepository: ProductOptionsJpaRepository
) {

    fun findById(id: UUID): ProductOptions? {
        return productOptionsJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<ProductOptions> {
        return productOptionsJpaRepository.findAll()
    }

    fun findByProductId(productId: UUID): List<ProductOptions> {
        return productOptionsJpaRepository.findByProductId(productId)
    }

    fun findByProductIdAndIsDefault(productId: UUID, isDefault: Boolean): List<ProductOptions> {
        return productOptionsJpaRepository.findByProductIdAndIsDefault(productId, isDefault)
    }

    fun existsByProductIdAndOptionNameAndOptionValue(productId: UUID, optionName: String, optionValue: String): Boolean {
        return productOptionsJpaRepository.existsByProductIdAndOptionNameAndOptionValue(productId, optionName, optionValue)
    }

    fun save(productOption: ProductOptions): ProductOptions {
        return productOptionsJpaRepository.save(productOption)
    }

    fun saveAll(productOptions: List<ProductOptions>): List<ProductOptions> {
        return productOptionsJpaRepository.saveAll(productOptions)
    }

    fun deleteById(id: UUID) {
        productOptionsJpaRepository.deleteById(id)
    }

    fun deleteAllById(ids: List<UUID>) {
        productOptionsJpaRepository.deleteAllById(ids)
    }

    fun deleteByProductId(productId: UUID) {
        productOptionsJpaRepository.deleteByProductId(productId)
    }
}
