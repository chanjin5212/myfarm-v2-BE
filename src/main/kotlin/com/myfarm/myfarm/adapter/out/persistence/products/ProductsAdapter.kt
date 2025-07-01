package com.myfarm.myfarm.adapter.out.persistence.products

import com.myfarm.myfarm.domain.products.entity.Products
import com.myfarm.myfarm.domain.products.entity.QProducts.products
import com.myfarm.myfarm.domain.products.port.ProductsSearchCommand
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductsAdapter(
    private val queryFactory: JPAQueryFactory
) {
    fun search(command: ProductsSearchCommand, pageable: Pageable): Page<Products> {
        val conditions = buildConditions(command)

        val content = queryFactory
            .selectFrom(products)
            .where(*conditions.toTypedArray())
            .orderBy(*buildOrderBy(command.sortBy).toTypedArray())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = queryFactory
            .select(products.count())
            .from(products)
            .where(*conditions.toTypedArray())
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }

    private fun buildConditions(command: ProductsSearchCommand): Collection<BooleanExpression?> {
        return listOf(
            products.status.eq(command.status),
            command.categoryId?.let { products.categoryId.eq(it) },
            command.keyword?.let {
                if (it.isBlank()) {
                    null
                } else {
                    products.name.containsIgnoreCase(it.trim())
                }
            },
            command.minPrice?.let { products.price.goe(it) },
            command.maxPrice?.let { products.price.loe(it) }
        )
    }

    private fun buildOrderBy(sortBy: String): Collection<OrderSpecifier<*>> {
        return when (sortBy) {
            "latest" -> listOf(products.createdAt.desc())
            "oldest" -> listOf(products.createdAt.asc())
            "popular" -> listOf(products.orderCount.desc())
            "priceAsc" -> listOf(products.price.asc())
            "priceDesc" -> listOf(products.price.desc())
            "name" -> listOf(products.name.asc())
            else -> listOf(products.createdAt.desc())
        }
    }
}
