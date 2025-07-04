package com.myfarm.myfarm.adapter.out.persistence.payments

import com.myfarm.myfarm.domain.payments.entity.Payments
import com.myfarm.myfarm.domain.payments.entity.QPayments
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PaymentsAdapter(
    private val queryFactory: JPAQueryFactory
) {

    fun search(
        status: String?,
        paymentProvider: String?,
        paymentMethod: String?,
        pageable: Pageable
    ): Page<Payments> {
        val qPayments = QPayments.payments
        val builder = BooleanBuilder()

        status?.let {
            builder.and(qPayments.status.eq(it))
        }

        paymentProvider?.let {
            builder.and(qPayments.paymentProvider.eq(it))
        }

        paymentMethod?.let {
            builder.and(qPayments.paymentMethod.eq(it))
        }

        val orderSpecifier = qPayments.createdAt.desc()

        val payments = queryFactory
            .selectFrom(qPayments)
            .where(builder)
            .orderBy(orderSpecifier)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(qPayments.count())
            .from(qPayments)
            .where(builder)
            .fetchOne() ?: 0L

        return PageImpl(payments, pageable, total)
    }
}
