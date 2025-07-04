package com.myfarm.myfarm.adapter.`in`.web.shipments

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.shipments.message.CreateShipment
import com.myfarm.myfarm.adapter.`in`.web.shipments.message.DeleteShipment
import com.myfarm.myfarm.adapter.`in`.web.shipments.message.GetShipment
import com.myfarm.myfarm.adapter.`in`.web.shipments.message.ListShipment
import com.myfarm.myfarm.adapter.`in`.web.shipments.message.UpdateShipment
import com.myfarm.myfarm.domain.common.checkAdminPermission
import com.myfarm.myfarm.domain.shipments.service.CreateShipmentService
import com.myfarm.myfarm.domain.shipments.service.DeleteShipmentService
import com.myfarm.myfarm.domain.shipments.service.GetShipmentService
import com.myfarm.myfarm.domain.shipments.service.ListShipmentService
import com.myfarm.myfarm.domain.shipments.service.UpdateShipmentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/shipments/v1")
class ShipmentsController(
    private val createShipmentService: CreateShipmentService,
    private val getShipmentService: GetShipmentService,
    private val updateShipmentService: UpdateShipmentService,
    private val deleteShipmentService: DeleteShipmentService,
    private val listShipmentService: ListShipmentService
) {

    @PostMapping
    fun createShipment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreateShipment.Request
    ): CreateShipment.Response {
        myfarmAuth.checkAdminPermission()
        return createShipmentService.createShipment(request)
    }

    @GetMapping("/{id}")
    fun getShipment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: GetShipment.PathVariable
    ): GetShipment.Response {
        myfarmAuth.checkAdminPermission()
        return getShipmentService.getShipment(request.id)
    }

    @PutMapping
    fun updateShipment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: UpdateShipment.Request
    ): UpdateShipment.Response {
        myfarmAuth.checkAdminPermission()
        return updateShipmentService.updateShipment(request)
    }

    @DeleteMapping
    fun deleteShipment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: DeleteShipment.Request
    ): DeleteShipment.Response {
        myfarmAuth.checkAdminPermission()
        return deleteShipmentService.deleteShipment(request)
    }

    @GetMapping
    fun listShipment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: ListShipment.Request
    ): ListShipment.Response {
        myfarmAuth.checkAdminPermission()
        return listShipmentService.listShipment(request)
    }
}
