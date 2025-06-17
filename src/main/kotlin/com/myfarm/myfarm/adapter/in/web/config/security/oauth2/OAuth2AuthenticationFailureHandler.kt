package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Component
class OAuth2AuthenticationFailureHandler(
    @Value("\${app.frontend.base-url}") private val frontendBaseUrl: String
) : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        val errorMessage = URLEncoder.encode(
            exception.localizedMessage ?: "OAuth2 로그인에 실패했습니다",
            StandardCharsets.UTF_8
        )

        val redirectUrl = "$frontendBaseUrl/login?error=$errorMessage"

        redirectStrategy.sendRedirect(request, response, redirectUrl)
    }
}
