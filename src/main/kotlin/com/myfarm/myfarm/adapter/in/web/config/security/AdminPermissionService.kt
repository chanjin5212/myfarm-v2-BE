package com.myfarm.myfarm.adapter.`in`.web.config.security

import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AdminPermissionService(
    private val usersRepository: UsersRepository
) {

    fun hasAdminRole(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            return false
        }

        val principal = authentication.principal
        if (principal is MyfarmAuthentication) {
            val user = usersRepository.findById(principal.userId)
            return user?.isAdmin == true
        }

        return false
    }

    fun hasAdminRole(userId: UUID): Boolean {
        val user = usersRepository.findById(userId)
        return user?.isAdmin == true
    }
}