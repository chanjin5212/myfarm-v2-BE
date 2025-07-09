package com.myfarm.myfarm.adapter.`in`.web.emailverifications

import com.myfarm.myfarm.adapter.`in`.web.emailverifications.message.SendVerification
import com.myfarm.myfarm.adapter.`in`.web.emailverifications.message.VerifyEmail
import com.myfarm.myfarm.domain.emailverifications.service.SendVerificationService
import com.myfarm.myfarm.domain.emailverifications.service.VerifyEmailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/email-verifications/v1")
class EmailVerificationController(
    private val sendVerificationService: SendVerificationService,
    private val verifyEmailService: VerifyEmailService
) {

    @PostMapping("/send")
    fun sendVerificationCode(
        @RequestBody request: SendVerification.Request
    ): SendVerification.Response {
        return sendVerificationService.sendVerificationCode(request)
    }

    @PostMapping("/verify")
    fun verifyEmail(
        @RequestBody request: VerifyEmail.Request
    ): VerifyEmail.Response {
        return verifyEmailService.verifyEmail(request)
    }
}
