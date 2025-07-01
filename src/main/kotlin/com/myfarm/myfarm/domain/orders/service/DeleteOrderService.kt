package com.myfarm.myfarm.domain.orders.service

import com.myfarm.myfarm.adapter.`in`.web.orders.message.DeleteOrder
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.orderitems.port.OrderItemsRepository
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteOrderService(
    private val ordersRepository: OrdersRepository,
    private val orderItemsRepository: OrderItemsRepository
) {

    @Transactional
    fun deleteOrder(userId: UUID, id: UUID): DeleteOrder.Response {
        val order = ordersRepository.findById(id)
            .checkOwnership(userId)

        if (order.status != "pending") {
            throw IllegalStateException("결제 완료 후에는 주문을 삭제할 수 없습니다")
        }

        orderItemsRepository.deleteByOrderId(id)

        ordersRepository.deleteById(id)

        return DeleteOrder.Response(success = true)
    }
}
