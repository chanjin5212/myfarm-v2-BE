package com.myfarm.myfarm.domain.shippingaddresses.port

import com.myfarm.myfarm.adapter.out.persistence.shippingaddresses.ShippingAddressesJpaRepository
import com.myfarm.myfarm.domain.shippingaddresses.entity.ShippingAddresses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ShippingAddressesRepository(
    private val shippingAddressesJpaRepository: ShippingAddressesJpaRepository
) {

    fun findById(id: UUID): ShippingAddresses? {
        return shippingAddressesJpaRepository.findById(id).getOrNull()
    }

    fun findByUserId(userId: UUID): List<ShippingAddresses> {
        return shippingAddressesJpaRepository.findByUserId(userId)
    }

    fun findByUserId(userId: UUID, pageable: Pageable): Page<ShippingAddresses> {
        return shippingAddressesJpaRepository.findByUserId(userId, pageable)
    }

    fun findByUserIdAndIsDefault(userId: UUID, isDefault: Boolean): ShippingAddresses? {
        return shippingAddressesJpaRepository.findByUserIdAndIsDefault(userId, isDefault)
    }

    fun existsByUserIdAndIsDefault(userId: UUID, isDefault: Boolean): Boolean {
        return shippingAddressesJpaRepository.existsByUserIdAndIsDefault(userId, isDefault)
    }

    fun save(shippingAddress: ShippingAddresses): ShippingAddresses {
        return shippingAddressesJpaRepository.save(shippingAddress)
    }

    fun deleteById(id: UUID) {
        shippingAddressesJpaRepository.deleteById(id)
    }
}
