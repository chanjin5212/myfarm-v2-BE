package com.myfarm.myfarm.domain.orders.service

import com.myfarm.myfarm.adapter.`in`.web.orders.message.CreateOrder
import com.myfarm.myfarm.domain.orderitems.entity.OrderItems
import com.myfarm.myfarm.domain.orderitems.port.OrderItemsRepository
import com.myfarm.myfarm.domain.orders.entity.Orders
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateOrderService(
    private val ordersRepository: OrdersRepository,
    private val orderItemsRepository: OrderItemsRepository,
    private val productsRepository: ProductsRepository,
    private val productOptionsRepository: ProductOptionsRepository
) {

    @Transactional
    fun createOrder(userId: UUID, request: CreateOrder.Request): CreateOrder.Response {
        if (request.shippingName.isBlank()) {
            throw IllegalArgumentException("배송받을 분 이름은 필수입니다")
        }

        if (request.shippingPhone.isBlank()) {
            throw IllegalArgumentException("배송받을 분 연락처는 필수입니다")
        }

        if (request.shippingAddress.isBlank()) {
            throw IllegalArgumentException("배송 주소는 필수입니다")
        }

        if (request.paymentMethod.isBlank()) {
            throw IllegalArgumentException("결제 방법은 필수입니다")
        }

        if (request.orderItems.isEmpty()) {
            throw IllegalArgumentException("주문 상품이 없습니다")
        }

        request.orderItems.forEach { orderItem ->
            productsRepository.findById(orderItem.productId)
                ?: throw IllegalArgumentException("존재하지 않는 상품입니다: ${orderItem.productId}")

            orderItem.productOptionId.let { optionId ->
                productOptionsRepository.findById(optionId)
                    ?: throw IllegalArgumentException("존재하지 않는 상품 옵션입니다: $optionId")
            }

            if (orderItem.quantity <= 0) {
                throw IllegalArgumentException("주문 수량은 1개 이상이어야 합니다")
            }

            if (orderItem.price < 0) {
                throw IllegalArgumentException("상품 가격은 0원 이상이어야 합니다")
            }
        }

        val now = LocalDateTime.now()
        val orderId = UUID.randomUUID()

        val totalAmount = request.orderItems.sumOf { it.price * it.quantity }

        val order = Orders(
            id = orderId,
            orderNumber = null,
            userId = userId,
            status = "pending",
            shippingName = request.shippingName,
            shippingPhone = request.shippingPhone,
            shippingAddress = request.shippingAddress,
            shippingDetailAddress = request.shippingDetailAddress,
            shippingMemo = request.shippingMemo,
            paymentMethod = request.paymentMethod,
            totalAmount = totalAmount,
            tid = null,
            cancelReason = null,
            cancelDate = null,
            createdAt = now,
            updatedAt = now
        )

        val savedOrder = ordersRepository.save(order)

        if (request.orderItems.isNotEmpty()) {
            val orderItems = request.orderItems.map { orderItemRequest ->
                OrderItems(
                    id = UUID.randomUUID(),
                    orderId = orderId,
                    productId = orderItemRequest.productId,
                    productOptionId = orderItemRequest.productOptionId,
                    quantity = orderItemRequest.quantity,
                    price = orderItemRequest.price,
                    options = orderItemRequest.options,
                    createdAt = now
                )
            }
            orderItemsRepository.saveAll(orderItems)
        }

        return CreateOrder.Response(
            success = true,
            orderId = savedOrder.id,
            orderNumber = savedOrder.orderNumber
        )
    }
}
