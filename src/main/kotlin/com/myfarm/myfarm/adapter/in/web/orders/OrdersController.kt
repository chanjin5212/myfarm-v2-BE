package com.myfarm.myfarm.adapter.`in`.web.orders

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.orders.message.CancelOrder
import com.myfarm.myfarm.adapter.`in`.web.orders.message.CreateOrder
import com.myfarm.myfarm.adapter.`in`.web.orders.message.DeleteOrder
import com.myfarm.myfarm.adapter.`in`.web.orders.message.GetOrder
import com.myfarm.myfarm.adapter.`in`.web.orders.message.ListOrder
import com.myfarm.myfarm.adapter.`in`.web.orders.message.UpdateOrder
import com.myfarm.myfarm.domain.orders.service.CancelOrderService
import com.myfarm.myfarm.domain.orders.service.CreateOrderService
import com.myfarm.myfarm.domain.orders.service.DeleteOrderService
import com.myfarm.myfarm.domain.orders.service.GetOrderService
import com.myfarm.myfarm.domain.orders.service.ListOrderService
import com.myfarm.myfarm.domain.orders.service.UpdateOrderService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders/v1")
class OrdersController(
    private val createOrderService: CreateOrderService,
    private val getOrderService: GetOrderService,
    private val listOrderService: ListOrderService,
    private val updateOrderService: UpdateOrderService,
    private val deleteOrderService: DeleteOrderService,
    private val cancelOrderService: CancelOrderService
) {

    @PostMapping
    fun createOrder(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateOrder.Request
    ): CreateOrder.Response {
        return createOrderService.createOrder(myfarmAuth.userId, request)
    }

    @GetMapping("/{id}")
    fun getOrder(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: GetOrder.PathVariable
    ): GetOrder.Response {
        return getOrderService.getOrder(myfarmAuth.userId, request.id)
    }

    @GetMapping
    fun listOrders(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: ListOrder.Request
    ): ListOrder.Response {
        return listOrderService.listOrders(myfarmAuth.userId, request)
    }

    @PutMapping("/{id}")
    fun updateOrder(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: UpdateOrder.PathVariable,
        @RequestBody updateRequest: UpdateOrder.Request
    ): UpdateOrder.Response {
        return updateOrderService.updateOrder(myfarmAuth.userId, request.id, updateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: DeleteOrder.PathVariable
    ): DeleteOrder.Response {
        return deleteOrderService.deleteOrder(myfarmAuth.userId, request.id)
    }

    @PatchMapping("/{id}/cancel")
    fun cancelOrder(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: CancelOrder.PathVariable,
        @RequestBody cancelRequest: CancelOrder.Request
    ): CancelOrder.Response {
        return cancelOrderService.cancelOrder(myfarmAuth.userId, request.id, cancelRequest)
    }
}
