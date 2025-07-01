package com.myfarm.myfarm.domain.carts.port

import com.myfarm.myfarm.adapter.out.persistence.carts.CartsJpaRepository
import com.myfarm.myfarm.domain.carts.entity.Carts
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class CartsRepository(
    private val cartsJpaRepository: CartsJpaRepository
) {

    fun findById(id: UUID): Carts? {
        return cartsJpaRepository.findById(id).getOrNull()
    }

    fun findByUserId(userId: UUID): Carts? {
        return cartsJpaRepository.findByUserId(userId)
    }

    fun existsByUserId(userId: UUID): Boolean {
        return cartsJpaRepository.existsByUserId(userId)
    }

    fun save(cart: Carts): Carts {
        return cartsJpaRepository.save(cart)
    }

    fun deleteById(id: UUID) {
        cartsJpaRepository.deleteById(id)
    }
}
