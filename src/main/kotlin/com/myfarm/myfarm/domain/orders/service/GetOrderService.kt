package com.myfarm.myfarm.domain.orders.service

import com.myfarm.myfarm.adapter.`in`.web.orders.message.GetOrder
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.orderitems.port.OrderItemsRepository
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetOrderService(
    private val ordersRepository: OrdersRepository,
    private val orderItemsRepository: OrderItemsRepository,
    private val productsRepository: ProductsRepository,
    private val productOptionsRepository: ProductOptionsRepository
) {

    @Transactional(readOnly = true)
    fun getOrder(userId: UUID, id: UUID): GetOrder.Response {
        val order = ordersRepository.findById(id)
            .checkOwnership(userId)

        val orderItems = orderItemsRepository.findByOrderId(id)

        val orderItemInfos = orderItems.map { orderItem ->
            val product = productsRepository.findById(orderItem.productId)
                ?: throw IllegalStateException("상품 정보를 찾을 수 없습니다")

            val optionName = orderItem.productOptionId.let { optionId ->
                productOptionsRepository.findById(optionId)?.let { option ->
                    "${option.optionName}: ${option.optionValue}"
                }
            }

            GetOrder.OrderItemInfo(
                id = orderItem.id,
                productId = orderItem.productId,
                productName = product.name,
                productOptionId = orderItem.productOptionId,
                optionName = optionName,
                quantity = orderItem.quantity,
                price = orderItem.price,
                totalPrice = orderItem.price * orderItem.quantity,
                options = orderItem.options,
                createdAt = orderItem.createdAt
            )
        }

        return GetOrder.Response(
            id = order.id,
            orderNumber = order.orderNumber,
            userId = order.userId,
            status = order.status,
            shippingName = order.shippingName,
            shippingPhone = order.shippingPhone,
            shippingAddress = order.shippingAddress,
            shippingDetailAddress = order.shippingDetailAddress,
            shippingMemo = order.shippingMemo,
            paymentMethod = order.paymentMethod,
            totalAmount = order.totalAmount,
            tid = order.tid,
            cancelReason = order.cancelReason,
            cancelDate = order.cancelDate,
            createdAt = order.createdAt,
            updatedAt = order.updatedAt,
            orderItems = orderItemInfos
        )
    }
}
