package com.myfarm.myfarm.adapter.`in`.web.config.security

import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class MyfarmAuthentication(
    private val email: String,
    private val password: String,
    val userId: UUID,
    val authority: GrantedAuthority
) : UserDetails, AuthenticatedPrincipal {

    override fun getAuthorities(): Collection<GrantedAuthority?>? = listOf(authority)

    override fun getPassword(): String? = password

    override fun getUsername(): String? = email

    override fun toString(): String {
        return "MyfarmAuthentication(email='$email', userId=$userId, authority=$authority)"
    }

    override fun getName(): String? = email
}
