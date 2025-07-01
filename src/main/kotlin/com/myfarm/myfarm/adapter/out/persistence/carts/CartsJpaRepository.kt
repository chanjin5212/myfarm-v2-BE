package com.myfarm.myfarm.adapter.out.persistence.carts

import com.myfarm.myfarm.domain.carts.entity.Carts
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CartsJpaRepository : JpaRepository<Carts, UUID> {

    fun findByUserId(userId: UUID): Carts?

    fun existsByUserId(userId: UUID): Boolean
}
