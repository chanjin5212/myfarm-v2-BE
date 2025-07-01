package com.myfarm.myfarm.domain.carts.service

import com.myfarm.myfarm.adapter.`in`.web.carts.message.GetCart
import com.myfarm.myfarm.domain.cartitems.port.CartItemsRepository
import com.myfarm.myfarm.domain.carts.port.CartsRepository
import com.myfarm.myfarm.domain.common.checkOwnership
import com.myfarm.myfarm.domain.productoptions.port.ProductOptionsRepository
import com.myfarm.myfarm.domain.products.port.ProductsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class GetCartService(
    private val cartsRepository: CartsRepository,
    private val cartItemsRepository: CartItemsRepository,
    private val productsRepository: ProductsRepository,
    private val productOptionsRepository: ProductOptionsRepository
) {

    @Transactional(readOnly = true)
    fun getCart(userId: UUID): GetCart.Response {
        val cart = cartsRepository.findByUserId(userId)
            .checkOwnership(userId)

        val cartItems = cartItemsRepository.findByCartId(cart.id)

        val cartItemInfos = cartItems.map { cartItem ->
            val product = productsRepository.findById(cartItem.productId)
                ?: throw IllegalArgumentException("상품이 존재하지 않습니다")

            val productOption = cartItem.productOptionId?.let { optionId ->
                productOptionsRepository.findById(optionId)
            }

            val additionalPrice = productOption?.additionalPrice ?: 0
            val totalPrice = (product.price + additionalPrice) * cartItem.quantity

            GetCart.CartItemInfo(
                id = cartItem.id,
                productId = cartItem.productId,
                productName = product.name,
                productPrice = product.price,
                productThumbnailUrl = product.thumbnailUrl,
                productOptionId = cartItem.productOptionId,
                productOptionName = productOption?.optionName,
                productOptionValue = productOption?.optionValue,
                additionalPrice = additionalPrice,
                quantity = cartItem.quantity,
                totalPrice = totalPrice
            )
        }

        val totalQuantity = cartItems.sumOf { it.quantity }

        return GetCart.Response(
            id = cart.id,
            userId = cart.userId,
            items = cartItemInfos,
            totalQuantity = totalQuantity,
            createdAt = cart.createdAt,
            updatedAt = cart.updatedAt
        )
    }
}
