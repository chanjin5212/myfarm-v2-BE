package com.myfarm.myfarm.adapter.`in`.web.shippingaddresses

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.CreateShippingAddress
import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.DeleteShippingAddress
import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.GetShippingAddress
import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.ListShippingAddress
import com.myfarm.myfarm.adapter.`in`.web.shippingaddresses.message.UpdateShippingAddress
import com.myfarm.myfarm.domain.shippingaddresses.service.CreateShippingAddressService
import com.myfarm.myfarm.domain.shippingaddresses.service.DeleteShippingAddressService
import com.myfarm.myfarm.domain.shippingaddresses.service.GetShippingAddressService
import com.myfarm.myfarm.domain.shippingaddresses.service.ListShippingAddressService
import com.myfarm.myfarm.domain.shippingaddresses.service.UpdateShippingAddressService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shipping-addresses/v1")
class ShippingAddressesController(
    private val createShippingAddressService: CreateShippingAddressService,
    private val getShippingAddressService: GetShippingAddressService,
    private val listShippingAddressService: ListShippingAddressService,
    private val updateShippingAddressService: UpdateShippingAddressService,
    private val deleteShippingAddressService: DeleteShippingAddressService
) {

    @PostMapping
    fun createShippingAddress(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateShippingAddress.Request
    ): CreateShippingAddress.Response {
        return createShippingAddressService.createShippingAddress(myfarmAuth.userId, request)
    }

    @GetMapping("/{id}")
    fun getShippingAddress(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: GetShippingAddress.PathVariable
    ): GetShippingAddress.Response {
        return getShippingAddressService.getShippingAddress(myfarmAuth.userId, request.id)
    }

    @GetMapping
    fun listShippingAddress(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: ListShippingAddress.Request
    ): ListShippingAddress.Response {
        return listShippingAddressService.listShippingAddress(myfarmAuth.userId, request)
    }

    @PutMapping("/{id}")
    fun updateShippingAddress(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: UpdateShippingAddress.PathVariable,
        @RequestBody updateRequest: UpdateShippingAddress.Request
    ): UpdateShippingAddress.Response {
        return updateShippingAddressService.updateShippingAddress(myfarmAuth.userId, request.id, updateRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteShippingAddress(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: DeleteShippingAddress.PathVariable
    ): DeleteShippingAddress.Response {
        return deleteShippingAddressService.deleteShippingAddress(myfarmAuth.userId, request.id)
    }
}
