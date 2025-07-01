package com.myfarm.myfarm.domain.carts.service

import com.myfarm.myfarm.adapter.`in`.web.carts.message.UpdateCart
import com.myfarm.myfarm.domain.cartitems.port.CartItemsRepository
import com.myfarm.myfarm.domain.carts.port.CartsRepository
import com.myfarm.myfarm.domain.common.checkOwnership
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.UUID

@Service
class UpdateCartService(
    private val cartsRepository: CartsRepository,
    private val cartItemsRepository: CartItemsRepository
) {

    @Transactional
    fun updateCart(userId: UUID, request: UpdateCart.Request): UpdateCart.Response {
        if (request.items.isEmpty()) {
            throw IllegalArgumentException("수정할 상품이 없습니다")
        }

        val cart = cartsRepository.findByUserId(userId)
            .checkOwnership(userId)

        var removedCount = 0
        var updatedCount = 0

        request.items.forEach { item ->
            val cartItem = cartItemsRepository.findByCartIdAndProductIdAndProductOptionId(
                cart.id,
                item.productId,
                item.productOptionId
            ) ?: throw IllegalArgumentException("장바구니에 해당 상품이 존재하지 않습니다: ${item.productId}")

            if (item.quantity <= 0) {
                cartItemsRepository.deleteById(cartItem.id)
                removedCount++
            } else {
                val updatedCartItem = cartItem.copy(
                    quantity = item.quantity,
                    updatedAt = LocalDateTime.now()
                )
                cartItemsRepository.save(updatedCartItem)
                updatedCount++
            }
        }

        val message = when {
            removedCount > 0 && updatedCount > 0 ->
                "${updatedCount}개 상품이 수정되고 ${removedCount}개 상품이 제거되었습니다"
            removedCount > 0 -> "${removedCount}개 상품이 장바구니에서 제거되었습니다"
            else -> "${updatedCount}개 상품의 수량이 변경되었습니다"
        }

        return UpdateCart.Response(
            success = true,
            message = message
        )
    }
}
