package com.myfarm.myfarm.adapter.`in`.web.config.security.authentication

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class MyfarmAuthenticationFailureHandler : AuthenticationFailureHandler {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}
