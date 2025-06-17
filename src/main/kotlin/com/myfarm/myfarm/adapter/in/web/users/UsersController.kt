package com.myfarm.myfarm.adapter.`in`.web.users

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.adapter.`in`.web.users.message.Me
import com.myfarm.myfarm.adapter.`in`.web.users.message.Register
import com.myfarm.myfarm.domain.users.service.RegisterService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/v1")
class UsersController(
    private val registerService: RegisterService
) {

    @GetMapping("/me")
    fun getCurrentUser(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication
    ): Me.Response {
        return Me.Response(
            id = myfarmAuth.userId,
            email = myfarmAuth.username
        )
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: Register.Request
    ): Register.Response {
        return registerService.register(request)
    }
}
