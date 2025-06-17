package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

import com.myfarm.myfarm.domain.users.entity.Users
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class MyfarmOAuth2User private constructor(
    private val user: Users,
    private val attributes: Map<String, Any>
) : OAuth2User {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()

        authorities.add(SimpleGrantedAuthority("ROLE_USER"))

        if (user.isAdmin) {
            authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        }

        return authorities
    }

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getName(): String = user.email

    val userId: UUID = user.id

    val email: String = user.email

    val userName: String = user.name ?: "Unknown"

    companion object {
        fun create(user: Users, attributes: Map<String, Any>): MyfarmOAuth2User {
            return MyfarmOAuth2User(user, attributes)
        }
    }
}
