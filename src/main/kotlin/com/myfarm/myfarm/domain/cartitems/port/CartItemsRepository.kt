package com.myfarm.myfarm.domain.cartitems.port

import com.myfarm.myfarm.adapter.out.persistence.cartitems.CartItemsJpaRepository
import com.myfarm.myfarm.domain.cartitems.entity.CartItems
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class CartItemsRepository(
    private val cartItemsJpaRepository: CartItemsJpaRepository
) {

    fun findById(id: UUID): CartItems? {
        return cartItemsJpaRepository.findById(id).getOrNull()
    }

    fun findByCartId(cartId: UUID): List<CartItems> {
        return cartItemsJpaRepository.findByCartId(cartId)
    }

    fun findByCartIdAndProductIdAndProductOptionId(
        cartId: UUID,
        productId: UUID,
        productOptionId: UUID?
    ): CartItems? {
        return cartItemsJpaRepository.findByCartIdAndProductIdAndProductOptionId(cartId, productId, productOptionId)
    }

    fun save(cartItem: CartItems): CartItems {
        return cartItemsJpaRepository.save(cartItem)
    }

    fun deleteById(id: UUID) {
        cartItemsJpaRepository.deleteById(id)
    }

    fun deleteByCartId(cartId: UUID) {
        cartItemsJpaRepository.deleteByCartId(cartId)
    }

    fun deleteByCartIdAndProductId(cartId: UUID, productId: UUID) {
        cartItemsJpaRepository.deleteByCartIdAndProductId(cartId, productId)
    }
}
