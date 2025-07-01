package com.myfarm.myfarm.domain.orders.port

import com.myfarm.myfarm.adapter.out.persistence.orders.OrdersAdapter
import com.myfarm.myfarm.adapter.out.persistence.orders.OrdersJpaRepository
import com.myfarm.myfarm.domain.orders.entity.Orders
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class OrdersRepository(
    private val ordersJpaRepository: OrdersJpaRepository,
    private val ordersAdapter: OrdersAdapter
) {

    fun findById(id: UUID): Orders? {
        return ordersJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<Orders> {
        return ordersJpaRepository.findAll()
    }

    fun findByUserId(userId: UUID): List<Orders> {
        return ordersJpaRepository.findByUserId(userId)
    }

    // QueryDSL을 사용한 복합 조건 검색
    fun findByFilters(
        userId: UUID,
        status: String? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        sortBy: String = "latest",
        page: Int = 0,
        size: Int = 20
    ): Page<Orders> {
        val pageable = PageRequest.of(page, size)
        val command = OrdersSearchCommand(
            userId = userId,
            status = status,
            startDate = startDate,
            endDate = endDate,
            sortBy = sortBy
        )

        return ordersAdapter.search(command, pageable)
    }

    fun save(order: Orders): Orders {
        return ordersJpaRepository.save(order)
    }

    fun deleteById(id: UUID) {
        ordersJpaRepository.deleteById(id)
    }

    fun existsById(id: UUID): Boolean {
        return ordersJpaRepository.existsById(id)
    }
}
