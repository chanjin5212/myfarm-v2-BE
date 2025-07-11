package com.myfarm.myfarm.adapter.out.persistence.orderitems

import com.myfarm.myfarm.domain.orderitems.entity.OrderItems
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderItemsJpaRepository : JpaRepository<OrderItems, UUID> {

    fun findByOrderId(orderId: UUID): List<OrderItems>

    fun findByProductId(productId: UUID): List<OrderItems>

    fun findByOrderIdAndProductId(orderId: UUID, productId: UUID): List<OrderItems>

    fun countByOrderId(orderId: UUID): Int // ← 이 메서드 추가됨

    fun deleteByOrderId(orderId: UUID)

    fun existsByOrderIdInAndProductId(orderIds: List<UUID>, productId: UUID): Boolean
}
