package com.myfarm.myfarm.domain.payments.service

import com.myfarm.myfarm.adapter.`in`.web.payments.message.ListPayment
import com.myfarm.myfarm.adapter.out.persistence.payments.PaymentsAdapter
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListPaymentService(
    private val paymentsAdapter: PaymentsAdapter
) {

    @Transactional(readOnly = true)
    fun listPayment(request: ListPayment.Request): ListPayment.Response {
        val pageable = PageRequest.of(request.page, request.size)

        val paymentPage = paymentsAdapter.search(
            status = request.status,
            paymentProvider = request.paymentProvider,
            paymentMethod = request.paymentMethod,
            pageable = pageable
        )

        val paymentInfoList = paymentPage.content.map { payment ->
            ListPayment.PaymentInfo(
                id = payment.id,
                orderId = payment.orderId,
                paymentKey = payment.paymentKey,
                paymentMethod = payment.paymentMethod,
                paymentProvider = payment.paymentProvider,
                amount = payment.amount,
                status = payment.status,
                createdAt = payment.createdAt,
                updatedAt = payment.updatedAt
            )
        }

        return ListPayment.Response(
            totalCount = paymentPage.totalElements.toInt(),
            payments = paymentInfoList
        )
    }
}
