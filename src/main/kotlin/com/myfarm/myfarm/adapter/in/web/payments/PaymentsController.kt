package com.myfarm.myfarm.adapter.`in`.web.payments

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.payments.message.CreatePayment
import com.myfarm.myfarm.adapter.`in`.web.payments.message.DeletePayment
import com.myfarm.myfarm.adapter.`in`.web.payments.message.GetPayment
import com.myfarm.myfarm.adapter.`in`.web.payments.message.ListPayment
import com.myfarm.myfarm.adapter.`in`.web.payments.message.UpdatePayment
import com.myfarm.myfarm.domain.common.checkAdminPermission
import com.myfarm.myfarm.domain.payments.service.CreatePaymentService
import com.myfarm.myfarm.domain.payments.service.DeletePaymentService
import com.myfarm.myfarm.domain.payments.service.GetPaymentService
import com.myfarm.myfarm.domain.payments.service.ListPaymentService
import com.myfarm.myfarm.domain.payments.service.UpdatePaymentService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payments/v1")
class PaymentsController(
    private val createPaymentService: CreatePaymentService,
    private val getPaymentService: GetPaymentService,
    private val updatePaymentService: UpdatePaymentService,
    private val deletePaymentService: DeletePaymentService,
    private val listPaymentService: ListPaymentService
) {

    @PostMapping
    fun createPayment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: CreatePayment.Request
    ): CreatePayment.Response {
        myfarmAuth.checkAdminPermission()
        return createPaymentService.createPayment(request)
    }

    @GetMapping("/{id}")
    fun getPayment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: GetPayment.PathVariable
    ): GetPayment.Response {
        myfarmAuth.checkAdminPermission()
        return getPaymentService.getPayment(request.id)
    }

    @PutMapping("/{id}")
    fun updatePayment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: UpdatePayment.PathVariable,
        @RequestBody updateRequest: UpdatePayment.Request
    ): UpdatePayment.Response {
        myfarmAuth.checkAdminPermission()
        return updatePaymentService.updatePayment(request.id, updateRequest)
    }

    @DeleteMapping
    fun deletePayment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @RequestBody request: DeletePayment.Request
    ): DeletePayment.Response {
        myfarmAuth.checkAdminPermission()
        return deletePaymentService.deletePayment(request)
    }

    @GetMapping
    fun listPayment(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        request: ListPayment.Request
    ): ListPayment.Response {
        myfarmAuth.checkAdminPermission()
        return listPaymentService.listPayment(request)
    }
}
