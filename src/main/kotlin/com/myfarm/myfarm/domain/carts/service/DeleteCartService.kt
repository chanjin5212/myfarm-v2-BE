package com.myfarm.myfarm.domain.carts.service

import com.myfarm.myfarm.adapter.`in`.web.carts.message.DeleteCart
import com.myfarm.myfarm.domain.cartitems.port.CartItemsRepository
import com.myfarm.myfarm.domain.carts.port.CartsRepository
import com.myfarm.myfarm.domain.common.checkOwnership
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DeleteCartService(
    private val cartsRepository: CartsRepository,
    private val cartItemsRepository: CartItemsRepository
) {

    @Transactional
    fun deleteCart(userId: UUID): DeleteCart.Response {
        val cart = cartsRepository.findByUserId(userId)
            .checkOwnership(userId)

        cartItemsRepository.deleteByCartId(cart.id)

        cartsRepository.deleteById(cart.id)

        return DeleteCart.Response(
            success = true,
            message = "장바구니가 삭제되었습니다"
        )
    }
}
