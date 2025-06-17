package com.myfarm.myfarm.adapter.`in`.web.config.security.authentication

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.domain.users.port.UsersRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

class MyfarmDevAuthorizationFilter(
    private val usersRepository: UsersRepository
) : OncePerRequestFilter() {

    companion object {
        const val AUTHORIZATION_HEADER = "myfarm-user-id"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val myfarmUserId = request.getHeader(AUTHORIZATION_HEADER)?.let { UUID.fromString(it) }
            ?: return filterChain.doFilter(request, response)

        val myfarmUser = usersRepository.findById(myfarmUserId)
            ?: return filterChain.doFilter(request, response)

        val authentication = MyfarmAuthentication(
            email = myfarmUser.email,
            password = "",
            userId = myfarmUserId,
            authority = SimpleGrantedAuthority(if (myfarmUser.isAdmin) "ROLE_ADMIN" else "ROLE_USER")
        )

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(
                authentication,
                null,
                listOf(authentication.authority)
            )

        filterChain.doFilter(request, response)
    }
}
