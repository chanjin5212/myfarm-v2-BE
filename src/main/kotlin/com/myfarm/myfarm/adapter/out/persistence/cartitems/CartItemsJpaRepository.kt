package com.myfarm.myfarm.adapter.out.persistence.cartitems

import com.myfarm.myfarm.domain.cartitems.entity.CartItems
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CartItemsJpaRepository : JpaRepository<CartItems, UUID> {

    fun findByCartId(cartId: UUID): List<CartItems>

    fun findByCartIdAndProductIdAndProductOptionId(
        cartId: UUID,
        productId: UUID,
        productOptionId: UUID?
    ): CartItems?

    fun deleteByCartId(cartId: UUID)

    fun deleteByCartIdAndProductId(cartId: UUID, productId: UUID)
}
