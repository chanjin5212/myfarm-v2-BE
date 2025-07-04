package com.myfarm.myfarm.domain.payments.service

import com.myfarm.myfarm.adapter.`in`.web.payments.message.UpdatePayment
import com.myfarm.myfarm.domain.payments.port.PaymentsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdatePaymentService(
    private val paymentsRepository: PaymentsRepository
) {

    @Transactional
    fun updatePayment(id: UUID, request: UpdatePayment.Request): UpdatePayment.Response {
        val existingPayment = paymentsRepository.findById(id)
            ?: throw IllegalArgumentException("존재하지 않는 결제 정보입니다")

        val updatedPayment = existingPayment.copy(
            status = request.status ?: existingPayment.status,
            paymentDetails = request.paymentDetails ?: existingPayment.paymentDetails,
            updatedAt = LocalDateTime.now()
        )

        paymentsRepository.save(updatedPayment)

        return UpdatePayment.Response(
            success = true,
            message = "결제 정보가 수정되었습니다"
        )
    }
}
