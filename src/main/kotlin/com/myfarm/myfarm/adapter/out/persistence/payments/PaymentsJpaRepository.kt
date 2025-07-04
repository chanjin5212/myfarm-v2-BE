package com.myfarm.myfarm.adapter.out.persistence.payments

import com.myfarm.myfarm.domain.payments.entity.Payments
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PaymentsJpaRepository : JpaRepository<Payments, UUID> {

    fun findByOrderId(orderId: UUID): Payments?

    fun findByPaymentKey(paymentKey: String): Payments?

    fun existsByOrderId(orderId: UUID): Boolean

    fun existsByPaymentKey(paymentKey: String): Boolean

    fun findByStatus(status: String): List<Payments>

    fun findByPaymentProvider(paymentProvider: String): List<Payments>
}
