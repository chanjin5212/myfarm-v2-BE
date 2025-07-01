package com.myfarm.myfarm.adapter.`in`.web.carts

import com.myfarm.myfarm.adapter.`in`.web.carts.message.CreateCart
import com.myfarm.myfarm.adapter.`in`.web.carts.message.DeleteCart
import com.myfarm.myfarm.adapter.`in`.web.carts.message.GetCart
import com.myfarm.myfarm.adapter.`in`.web.carts.message.RemoveCartItem
import com.myfarm.myfarm.adapter.`in`.web.carts.message.UpdateCart
import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.domain.carts.service.CreateCartService
import com.myfarm.myfarm.domain.carts.service.DeleteCartService
import com.myfarm.myfarm.domain.carts.service.GetCartService
import com.myfarm.myfarm.domain.carts.service.RemoveCartItemService
import com.myfarm.myfarm.domain.carts.service.UpdateCartService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/carts/v1")
class CartsController(
    private val createCartService: CreateCartService,
    private val updateCartService: UpdateCartService,
    private val getCartService: GetCartService,
    private val deleteCartService: DeleteCartService,
    private val removeCartItemService: RemoveCartItemService
) {

    @PostMapping
    fun createCart(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateCart.Request
    ): CreateCart.Response {
        return createCartService.createCart(myfarmAuth.userId, request)
    }

    @PutMapping
    fun updateCart(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: UpdateCart.Request
    ): UpdateCart.Response {
        return updateCartService.updateCart(myfarmAuth.userId, request)
    }

    @GetMapping
    fun getCart(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication
    ): GetCart.Response {
        return getCartService.getCart(myfarmAuth.userId)
    }

    @DeleteMapping
    fun deleteCart(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication
    ): DeleteCart.Response {
        return deleteCartService.deleteCart(myfarmAuth.userId)
    }

    @DeleteMapping("/items")
    fun removeCartItem(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: RemoveCartItem.Request
    ): RemoveCartItem.Response {
        return removeCartItemService.removeCartItem(myfarmAuth.userId, request)
    }
}
