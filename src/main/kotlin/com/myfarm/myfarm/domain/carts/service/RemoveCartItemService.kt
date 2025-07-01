package com.myfarm.myfarm.domain.carts.service

import com.myfarm.myfarm.adapter.`in`.web.carts.message.RemoveCartItem
import com.myfarm.myfarm.domain.cartitems.port.CartItemsRepository
import com.myfarm.myfarm.domain.carts.port.CartsRepository
import com.myfarm.myfarm.domain.common.checkOwnership
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RemoveCartItemService(
    private val cartsRepository: CartsRepository,
    private val cartItemsRepository: CartItemsRepository
) {

    @Transactional
    fun removeCartItem(userId: UUID, request: RemoveCartItem.Request): RemoveCartItem.Response {
        if (request.items.isEmpty()) {
            throw IllegalArgumentException("제거할 상품이 없습니다")
        }

        val cart = cartsRepository.findByUserId(userId)
            .checkOwnership(userId)

        var removedCount = 0

        request.items.forEach { item ->
            val cartItem = cartItemsRepository.findByCartIdAndProductIdAndProductOptionId(
                cart.id,
                item.productId,
                item.productOptionId
            ) ?: throw IllegalArgumentException("장바구니에 해당 상품이 존재하지 않습니다: ${item.productId}")

            cartItemsRepository.deleteById(cartItem.id)
            removedCount++
        }

        return RemoveCartItem.Response(
            success = true,
            message = "${removedCount}개 상품이 장바구니에서 제거되었습니다"
        )
    }
}
