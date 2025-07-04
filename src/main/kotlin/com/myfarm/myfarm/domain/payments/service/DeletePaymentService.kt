package com.myfarm.myfarm.domain.payments.service

import com.myfarm.myfarm.adapter.`in`.web.payments.message.DeletePayment
import com.myfarm.myfarm.domain.payments.port.PaymentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeletePaymentService(
    private val paymentsRepository: PaymentsRepository
) {

    @Transactional
    fun deletePayment(request: DeletePayment.Request): DeletePayment.Response {
        val existingPayments = mutableListOf<String>()

        request.paymentIds.forEach { paymentId ->
            val payment = paymentsRepository.findById(paymentId)
                ?: throw IllegalArgumentException("존재하지 않는 결제 정보입니다: $paymentId")

            existingPayments.add(payment.paymentKey)
        }

        paymentsRepository.deleteAllById(request.paymentIds)

        return DeletePayment.Response(
            success = true,
            message = "${existingPayments.size}개의 결제 정보가 삭제되었습니다"
        )
    }
}
