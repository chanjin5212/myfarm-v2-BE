package com.myfarm.myfarm.domain.orderitems.port

import com.myfarm.myfarm.adapter.out.persistence.orderitems.OrderItemsJpaRepository
import com.myfarm.myfarm.domain.orderitems.entity.OrderItems
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class OrderItemsRepository(
    private val orderItemsJpaRepository: OrderItemsJpaRepository
) {

    fun findById(id: UUID): OrderItems? {
        return orderItemsJpaRepository.findById(id).getOrNull()
    }

    fun findAll(): List<OrderItems> {
        return orderItemsJpaRepository.findAll()
    }

    fun findByOrderId(orderId: UUID): List<OrderItems> {
        return orderItemsJpaRepository.findByOrderId(orderId)
    }

    fun findByProductId(productId: UUID): List<OrderItems> {
        return orderItemsJpaRepository.findByProductId(productId)
    }

    fun findByOrderIdAndProductId(orderId: UUID, productId: UUID): List<OrderItems> {
        return orderItemsJpaRepository.findByOrderIdAndProductId(orderId, productId)
    }

    fun countByOrderId(orderId: UUID): Int { // ← 이 메서드 추가됨
        return orderItemsJpaRepository.countByOrderId(orderId)
    }

    fun save(orderItem: OrderItems): OrderItems {
        return orderItemsJpaRepository.save(orderItem)
    }

    fun saveAll(orderItems: List<OrderItems>): List<OrderItems> {
        return orderItemsJpaRepository.saveAll(orderItems)
    }

    fun deleteById(id: UUID) {
        orderItemsJpaRepository.deleteById(id)
    }

    fun deleteAllById(ids: List<UUID>) {
        orderItemsJpaRepository.deleteAllById(ids)
    }

    fun deleteByOrderId(orderId: UUID) {
        orderItemsJpaRepository.deleteByOrderId(orderId)
    }
}
