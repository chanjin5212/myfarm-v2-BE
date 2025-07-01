package com.myfarm.myfarm.adapter.out.persistence.orders

import com.myfarm.myfarm.domain.orders.entity.Orders
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.UUID

interface OrdersJpaRepository : JpaRepository<Orders, UUID> {

    fun findByUserId(userId: UUID): List<Orders>

    fun findByUserIdOrderByCreatedAtDesc(userId: UUID): List<Orders>

    fun findByStatus(status: String): List<Orders>

    fun findByOrderNumber(orderNumber: String): Orders?

    fun findByCancelDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Orders>

    fun existsByOrderNumber(orderNumber: String): Boolean
}
