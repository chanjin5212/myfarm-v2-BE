package com.myfarm.myfarm.adapter.out.persistence.products

import com.myfarm.myfarm.domain.products.entity.Products
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductsJpaRepository : JpaRepository<Products, UUID> {

    fun findBySellerId(sellerId: UUID): List<Products>
    fun findByStatus(status: Products.Status): List<Products>
    fun findByStatusAndSellerId(status: Products.Status, sellerId: UUID): List<Products>
}
