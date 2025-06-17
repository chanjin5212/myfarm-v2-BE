package com.myfarm.myfarm.adapter.`in`.web.config.security.authentication

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyfarmUserDetailsService(
    private val usersRepository: UsersRepository
) : UserDetailsService {

    override fun loadUserByUsername(loginId: String): UserDetails? {
        val user = usersRepository.findByLoginId(loginId)
            ?: throw UsernameNotFoundException("user not found with loginId: $loginId")

        return MyfarmAuthentication(
            email = user.email,
            password = user.password ?: "",
            userId = user.id,
            authority = SimpleGrantedAuthority(if (user.isAdmin) "ROLE_ADMIN" else "ROLE_USER")
        )
    }
}
