package com.myfarm.myfarm.domain.orders.service

import com.myfarm.myfarm.adapter.`in`.web.orders.message.CancelOrder
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.orderitems.port.OrderItemsRepository
import com.myfarm.myfarm.domain.orders.entity.Orders
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CancelOrderService(
    private val ordersRepository: OrdersRepository,
    private val orderItemsRepository: OrderItemsRepository,
    private val productOptionsRepository: ProductOptionsRepository
) {

    @Transactional
    fun cancelOrder(userId: UUID, id: UUID, request: CancelOrder.Request): CancelOrder.Response {
        val order = ordersRepository.findById(id)
            .checkOwnership(userId)

        if (!isCancellable(order)) {
            throw IllegalStateException("현재 주문 상태에서는 취소할 수 없습니다. 상태: ${order.status}")
        }

        if (request.cancelReason.isBlank()) {
            throw IllegalArgumentException("취소 사유는 필수입니다")
        }

        val now = LocalDateTime.now()

        // TODO: 결제 취소 API 호출
        // paymentCancelService.cancelPayment(order.tid, request.cancelReason)

        // 재고 복구 처리
        restoreStock(order.id)

        val cancelledOrder = Orders(
            id = order.id,
            orderNumber = order.orderNumber,
            userId = order.userId,
            status = "cancelled",
            shippingName = order.shippingName,
            shippingPhone = order.shippingPhone,
            shippingAddress = order.shippingAddress,
            shippingDetailAddress = order.shippingDetailAddress,
            shippingMemo = order.shippingMemo,
            paymentMethod = order.paymentMethod,
            totalAmount = order.totalAmount,
            tid = order.tid,
            cancelReason = request.cancelReason,
            cancelDate = now,
            createdAt = order.createdAt,
            updatedAt = now
        )

        ordersRepository.save(cancelledOrder)

        return CancelOrder.Response(
            success = true,
            message = "주문이 성공적으로 취소되었습니다."
        )
    }

    private fun restoreStock(orderId: UUID) {
        val orderItems = orderItemsRepository.findByOrderId(orderId)
        
        orderItems.forEach { orderItem ->
            val option = productOptionsRepository.findById(orderItem.productOptionId)
                ?: return@forEach // 옵션이 삭제된 경우 스킵
            
            val restoredOption = option.copy(
                stock = option.stock + orderItem.quantity,
                updatedAt = LocalDateTime.now()
            )
            productOptionsRepository.save(restoredOption)
        }
    }

    private fun isCancellable(order: Orders): Boolean {
        return when (order.status) {
            "pending" -> true // 결제 대기 중
            "paid" -> true // 결제 완료
            "preparing" -> true // 상품 준비 중
            "shipped" -> false // 배송 시작 후에는 취소 불가
            "delivered" -> false // 배송 완료 후에는 취소 불가
            "cancelled" -> false // 이미 취소됨
            else -> false
        }
    }
}
