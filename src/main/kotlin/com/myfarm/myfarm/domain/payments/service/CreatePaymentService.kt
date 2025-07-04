package com.myfarm.myfarm.domain.payments.service

import com.myfarm.myfarm.adapter.`in`.web.payments.message.CreatePayment
import com.myfarm.myfarm.domain.orders.port.OrdersRepository
import com.myfarm.myfarm.domain.payments.entity.Payments
import com.myfarm.myfarm.domain.payments.port.PaymentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreatePaymentService(
    private val paymentsRepository: PaymentsRepository,
    private val ordersRepository: OrdersRepository
) {

    @Transactional
    fun createPayment(request: CreatePayment.Request): CreatePayment.Response {
        val order = ordersRepository.findById(request.orderId)
            ?: throw IllegalArgumentException("존재하지 않는 주문입니다: ${request.orderId}")

        if (paymentsRepository.existsByOrderId(request.orderId)) {
            throw IllegalArgumentException("이미 결제 정보가 등록된 주문입니다: ${request.orderId}")
        }

        if (paymentsRepository.existsByPaymentKey(request.paymentKey)) {
            throw IllegalArgumentException("이미 사용 중인 결제 키입니다: ${request.paymentKey}")
        }

        if (request.amount != order.totalAmount) {
            throw IllegalArgumentException("결제 금액이 주문 금액과 일치하지 않습니다. 주문금액: ${order.totalAmount}, 결제금액: ${request.amount}")
        }

        val now = LocalDateTime.now()
        val payment = Payments(
            id = UUID.randomUUID(),
            orderId = request.orderId,
            paymentKey = request.paymentKey,
            paymentMethod = request.paymentMethod,
            paymentProvider = request.paymentProvider,
            amount = request.amount,
            status = request.status,
            paymentDetails = request.paymentDetails,
            createdAt = now,
            updatedAt = now
        )

        val savedPayment = paymentsRepository.save(payment)

        return CreatePayment.Response(
            success = true,
            paymentId = savedPayment.id,
            message = "결제 정보가 등록되었습니다"
        )
    }
}
