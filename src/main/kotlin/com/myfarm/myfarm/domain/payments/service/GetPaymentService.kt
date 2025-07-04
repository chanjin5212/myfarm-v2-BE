package com.myfarm.myfarm.domain.payments.service

import com.myfarm.myfarm.adapter.`in`.web.payments.message.GetPayment
import com.myfarm.myfarm.domain.payments.port.PaymentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetPaymentService(
    private val paymentsRepository: PaymentsRepository
) {

    @Transactional(readOnly = true)
    fun getPayment(id: UUID): GetPayment.Response {
        val payment = paymentsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 결제 정보입니다")

        return GetPayment.Response(
            id = payment.id,
            orderId = payment.orderId,
            paymentKey = payment.paymentKey,
            paymentMethod = payment.paymentMethod,
            paymentProvider = payment.paymentProvider,
            amount = payment.amount,
            status = payment.status,
            paymentDetails = payment.paymentDetails,
            createdAt = payment.createdAt,
            updatedAt = payment.updatedAt
        )
    }
}
