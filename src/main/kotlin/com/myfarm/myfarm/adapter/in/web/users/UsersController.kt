package com.myfarm.myfarm.adapter.`in`.web.users

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.users.message.CheckDuplicate
import com.myfarm.myfarm.adapter.`in`.web.users.message.FindId
import com.myfarm.myfarm.adapter.`in`.web.users.message.FindPassword
import com.myfarm.myfarm.adapter.`in`.web.users.message.Me
import com.myfarm.myfarm.adapter.`in`.web.users.message.Register
import com.myfarm.myfarm.adapter.`in`.web.users.message.ResetPassword
import com.myfarm.myfarm.domain.users.service.CheckDuplicateService
import com.myfarm.myfarm.domain.users.service.FindIdService
import com.myfarm.myfarm.domain.users.service.FindPasswordService
import com.myfarm.myfarm.domain.users.service.MeService
import com.myfarm.myfarm.domain.users.service.RegisterService
import com.myfarm.myfarm.domain.users.service.ResetPasswordService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/v1")
class UsersController(
    private val registerService: RegisterService,
    private val checkDuplicateService: CheckDuplicateService,
    private val findIdService: FindIdService,
    private val findPasswordService: FindPasswordService,
    private val resetPasswordService: ResetPasswordService,
    private val meService: MeService
) {

    @GetMapping("/me")
    fun getCurrentUser(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication
    ): Me.Response {
        return meService.getCurrentUser(myfarmAuth.userId)
    }


    @PostMapping("/register")
    fun register(
        @RequestBody request: Register.Request
    ): Register.Response {
        return registerService.register(request)
    }

    @PostMapping("/check-duplicate")
    fun checkDuplicate(
        @RequestBody request: CheckDuplicate.Request
    ): CheckDuplicate.Response {
        return checkDuplicateService.checkDuplicate(request)
    }

    @PostMapping("/find-id")
    fun findId(
        @RequestBody request: FindId.Request
    ): FindId.Response {
        return findIdService.findId(request)
    }

    @PostMapping("/find-password")
    fun findPassword(
        @RequestBody request: FindPassword.Request
    ): FindPassword.Response {
        return findPasswordService.findPassword(request)
    }

    @PostMapping("/reset-password")
    fun resetPassword(
        @RequestBody request: ResetPassword.Request
    ): ResetPassword.Response {
        return resetPasswordService.resetPassword(request)
    }
}
