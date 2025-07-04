package com.myfarm.myfarm.adapter.out.persistence.shipments

import com.myfarm.myfarm.domain.shipments.entity.QShipments
import com.myfarm.myfarm.domain.shipments.entity.Shipments
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ShipmentsAdapter(
    private val queryFactory: JPAQueryFactory
) {

    fun search(
        status: String?,
        carrier: String?,
        pageable: Pageable
    ): Page<Shipments> {
        val qShipments = QShipments.shipments
        val builder = BooleanBuilder()

        status?.let {
            builder.and(qShipments.status.eq(it))
        }

        carrier?.let {
            builder.and(qShipments.carrier.eq(it))
        }

        val orderSpecifier = qShipments.createdAt.desc()

        val shipments = queryFactory
            .selectFrom(qShipments)
            .where(builder)
            .orderBy(orderSpecifier)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(qShipments.count())
            .from(qShipments)
            .where(builder)
            .fetchOne() ?: 0L

        return PageImpl(shipments, pageable, total)
    }
}
