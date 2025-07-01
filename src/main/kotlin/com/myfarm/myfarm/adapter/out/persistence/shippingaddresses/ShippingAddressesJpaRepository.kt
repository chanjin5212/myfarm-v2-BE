package com.myfarm.myfarm.adapter.out.persistence.shippingaddresses

import com.myfarm.myfarm.domain.shippingaddresses.entity.ShippingAddresses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ShippingAddressesJpaRepository : JpaRepository<ShippingAddresses, UUID> {

    fun findByUserId(userId: UUID): List<ShippingAddresses>

    fun findByUserId(userId: UUID, pageable: Pageable): Page<ShippingAddresses>

    fun findByUserIdAndIsDefault(userId: UUID, isDefault: Boolean): ShippingAddresses?

    fun existsByUserIdAndIsDefault(userId: UUID, isDefault: Boolean): Boolean
}