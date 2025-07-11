package com.myfarm.myfarm.domain.products.port

import com.myfarm.myfarm.adapter.out.persistence.products.ProductsAdapter
import com.myfarm.myfarm.adapter.out.persistence.products.ProductsJpaRepository
import com.myfarm.myfarm.domain.products.entity.Products
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ProductsRepository(
    private val productsJpaRepository: ProductsJpaRepository,
    private val productsAdapter: ProductsAdapter
) {

    fun findById(id: UUID): Products? {
        return productsJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<Products> {
        return productsJpaRepository.findAll()
    }

    fun findBySellerId(sellerId: UUID): List<Products> {
        return productsJpaRepository.findBySellerId(sellerId)
    }

    fun findByStatus(status: Products.Status): List<Products> {
        return productsJpaRepository.findByStatus(status)
    }

    fun findByStatusAndSellerId(status: Products.Status, sellerId: UUID): List<Products> {
        return productsJpaRepository.findByStatusAndSellerId(status, sellerId)
    }

    fun findByFilters(
        status: Products.Status = Products.Status.ACTIVE,
        categoryId: UUID? = null,
        keyword: String? = null,
        minPrice: Int? = null,
        maxPrice: Int? = null,
        sortBy: String = "latest",
        page: Int = 0,
        size: Int = 20
    ): Page<Products> {
        val pageable = PageRequest.of(page, size)
        val command = ProductsSearchCommand(
            status = status,
            categoryId = categoryId,
            keyword = keyword,
            minPrice = minPrice,
            maxPrice = maxPrice,
            sortBy = sortBy
        )

        return productsAdapter.search(command, pageable)
    }

    fun save(product: Products): Products {
        return productsJpaRepository.save(product)
    }

    fun deleteById(id: UUID) {
        productsJpaRepository.deleteById(id)
    }

    fun existsById(id: UUID): Boolean {
        return productsJpaRepository.existsById(id)
    }
}
