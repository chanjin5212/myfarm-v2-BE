package com.myfarm.myfarm.adapter.`in`.web.config.security.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.SecurityContextRepository

class MyfarmAuthenticationFilter(
    securityContextRepository: SecurityContextRepository,
    private val authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter() {

    companion object {
        const val AUTHENTICATION_ENTRY_POINT = "/api/users/v1/login"
    }

    init {
        setFilterProcessesUrl(AUTHENTICATION_ENTRY_POINT)
        setAuthenticationSuccessHandler(
            MyfarmAuthenticationSuccessHandler(securityContextRepository)
        )
        setAuthenticationFailureHandler(MyfarmAuthenticationFailureHandler())
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val myfarmLoginRequest = request.toMyfarmLoginRequest()

        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                myfarmLoginRequest.loginId,
                myfarmLoginRequest.password
            )
        )
    }

    private fun HttpServletRequest.toMyfarmLoginRequest(): MyfarmLoginRequest {
        return try {
            objectMapper.readValue(this.inputStream, MyfarmLoginRequest::class.java)
        } catch (e: Exception) {
            throw RuntimeException("Invalid login request", e)
        }
    }

    data class MyfarmLoginRequest(
        val loginId: String,
        val password: String
    )
}
