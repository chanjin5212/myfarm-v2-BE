package com.myfarm.myfarm.domain.carts.service

import com.myfarm.myfarm.adapter.`in`.web.carts.message.CreateCart
import com.myfarm.myfarm.domain.cartitems.entity.CartItems
import com.myfarm.myfarm.domain.cartitems.port.CartItemsRepository
import com.myfarm.myfarm.domain.carts.entity.Carts
import com.myfarm.myfarm.domain.carts.port.CartsRepository
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateCartService(
    private val cartsRepository: CartsRepository,
    private val cartItemsRepository: CartItemsRepository,
    private val productsRepository: ProductsRepository,
    private val productOptionsRepository: ProductOptionsRepository
) {

    @Transactional
    fun createCart(userId: UUID, request: CreateCart.Request): CreateCart.Response {
        if (request.items.isEmpty()) {
            throw IllegalArgumentException("장바구니에 담을 상품이 없습니다")
        }

        request.items.forEach { item ->
            productsRepository.findById(item.productId)
                ?: throw IllegalArgumentException("존재하지 않는 상품입니다: ${item.productId}")

            productOptionsRepository.findById(item.productOptionId)
                ?: throw IllegalArgumentException("존재하지 않는 상품 옵션입니다: ${item.productOptionId}")

            if (item.quantity <= 0) {
                throw IllegalArgumentException("상품 수량은 1개 이상이어야 합니다")
            }
        }

        val existingCart = cartsRepository.findByUserId(userId)

        val cart = if (existingCart != null) {
            existingCart
        } else {
            val now = LocalDateTime.now()
            val newCart = Carts(
                id = UUID.randomUUID(),
                userId = userId,
                createdAt = now,
                updatedAt = now
            )
            cartsRepository.save(newCart)
        }

        request.items.forEach { item ->
            val existingCartItem = cartItemsRepository.findByCartIdAndProductIdAndProductOptionId(
                cart.id,
                item.productId,
                item.productOptionId
            )

            if (existingCartItem != null) {
                val updatedCartItem = existingCartItem.copy(
                    quantity = existingCartItem.quantity + item.quantity,
                    updatedAt = LocalDateTime.now()
                )
                cartItemsRepository.save(updatedCartItem)
            } else {
                val now = LocalDateTime.now()
                val cartItem = CartItems(
                    id = UUID.randomUUID(),
                    cartId = cart.id,
                    productId = item.productId,
                    productOptionId = item.productOptionId,
                    quantity = item.quantity,
                    createdAt = now,
                    updatedAt = now
                )
                cartItemsRepository.save(cartItem)
            }
        }

        return CreateCart.Response(
            success = true,
            message = "장바구니에 ${request.items.size}개 상품이 담겼습니다"
        )
    }
}
