package com.myfarm.myfarm.domain.payments.port

import com.myfarm.myfarm.adapter.out.persistence.payments.PaymentsJpaRepository
import com.myfarm.myfarm.domain.payments.entity.Payments
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class PaymentsRepository(
    private val paymentsJpaRepository: PaymentsJpaRepository
) {

    fun findById(id: UUID): Payments? {
        return paymentsJpaRepository.findById(id).getOrNull()
    }

    fun findByOrderId(orderId: UUID): Payments? {
        return paymentsJpaRepository.findByOrderId(orderId)
    }

    fun findByPaymentKey(paymentKey: String): Payments? {
        return paymentsJpaRepository.findByPaymentKey(paymentKey)
    }

    fun existsByOrderId(orderId: UUID): Boolean {
        return paymentsJpaRepository.existsByOrderId(orderId)
    }

    fun existsByPaymentKey(paymentKey: String): Boolean {
        return paymentsJpaRepository.existsByPaymentKey(paymentKey)
    }

    fun findByStatus(status: String): List<Payments> {
        return paymentsJpaRepository.findByStatus(status)
    }

    fun findByPaymentProvider(paymentProvider: String): List<Payments> {
        return paymentsJpaRepository.findByPaymentProvider(paymentProvider)
    }

    fun save(payment: Payments): Payments {
        return paymentsJpaRepository.save(payment)
    }

    fun saveAll(payments: List<Payments>): List<Payments> {
        return paymentsJpaRepository.saveAll(payments)
    }

    fun deleteById(id: UUID) {
        paymentsJpaRepository.deleteById(id)
    }

    fun deleteAllById(ids: List<UUID>) {
        paymentsJpaRepository.deleteAllById(ids)
    }
}
