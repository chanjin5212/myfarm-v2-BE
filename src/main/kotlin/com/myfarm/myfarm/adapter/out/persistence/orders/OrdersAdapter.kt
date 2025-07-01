package com.myfarm.myfarm.adapter.out.persistence.orders

import com.myfarm.myfarm.domain.orders.entity.Orders
import com.myfarm.myfarm.domain.orders.entity.QOrders
import com.myfarm.myfarm.domain.orders.port.OrdersSearchCommand
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class OrdersAdapter(
    private val queryFactory: JPAQueryFactory
) {

    fun search(command: OrdersSearchCommand, pageable: Pageable): Page<Orders> {
        val qOrders = QOrders.orders
        val builder = BooleanBuilder()

        // 사용자 필터
        builder.and(qOrders.userId.eq(command.userId))

        // 상태 필터
        command.status?.let {
            builder.and(qOrders.status.eq(it))
        }

        // 날짜 범위 필터
        command.startDate?.let {
            builder.and(qOrders.createdAt.goe(it))
        }

        command.endDate?.let {
            builder.and(qOrders.createdAt.loe(it))
        }

        // 정렬 설정
        val orderSpecifier = when (command.sortBy) {
            "oldest" -> qOrders.createdAt.asc()
            else -> qOrders.createdAt.desc() // latest
        }

        // 데이터 조회
        val orders = queryFactory
            .selectFrom(qOrders)
            .where(builder)
            .orderBy(orderSpecifier)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // 총 개수 조회 (deprecated fetchCount 대신 사용)
        val total = queryFactory
            .select(qOrders.count())
            .from(qOrders)
            .where(builder)
            .fetchOne() ?: 0L

        return PageImpl(orders, pageable, total)
    }
}
