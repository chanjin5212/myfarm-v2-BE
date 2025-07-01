package com.myfarm.myfarm.domain.orders.service

import com.myfarm.myfarm.adapter.`in`.web.orders.message.ListOrder
import com.myfarm.myfarm.domain.orderitems.port.OrderItemsRepository
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class ListOrderService(
    private val ordersRepository: OrdersRepository,
    private val orderItemsRepository: OrderItemsRepository
) {

    @Transactional(readOnly = true)
    fun listOrders(userId: UUID, request: ListOrder.Request): ListOrder.Response {
        val startDate = request.startDate?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay()
        }
        val endDate = request.endDate?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE).atTime(23, 59, 59)
        }

        val ordersPage = ordersRepository.findByFilters(
            userId = userId,
            status = request.status,
            startDate = startDate,
            endDate = endDate,
            sortBy = request.sortBy,
            page = request.page,
            size = request.size
        )

        val orderSummaries = ordersPage.content.map { order ->
            val itemCount = orderItemsRepository.countByOrderId(order.id)

            ListOrder.OrderSummary(
                id = order.id,
                orderNumber = order.orderNumber,
                status = order.status,
                totalAmount = order.totalAmount,
                itemCount = itemCount,
                createdAt = order.createdAt
            )
        }

        return ListOrder.Response(
            totalCount = ordersPage.totalElements.toInt(),
            orders = orderSummaries
        )
    }
}
