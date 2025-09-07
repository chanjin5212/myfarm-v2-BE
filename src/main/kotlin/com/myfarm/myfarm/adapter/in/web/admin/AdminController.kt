package com.myfarm.myfarm.adapter.`in`.web.admin

import com.myfarm.myfarm.adapter.`in`.web.admin.message.GetDashboard
import com.myfarm.myfarm.adapter.`in`.web.admin.message.ListUsers
import com.myfarm.myfarm.adapter.`in`.web.admin.message.ToggleAdminStatus
import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/admin/v1")
class AdminController(
    private val usersRepository: UsersRepository
) {

    @PreAuthorize("@adminPermissionService.hasAdminRole()")
    @GetMapping("/dashboard")
    fun getDashboard(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication
    ): GetDashboard.Response {
        return GetDashboard.Response(
            message = "관리자 대시보드",
            userId = myfarmAuth.userId,
            totalUsers = usersRepository.count()
        )
    }

    @PreAuthorize("@adminPermissionService.hasAdminRole()")
    @GetMapping("/users")
    fun listUsers(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication
    ): ListUsers.Response {
        val users = usersRepository.findAll()
        return ListUsers.Response(
            users = users.map { user ->
                ListUsers.UserSummary(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    isAdmin = user.isAdmin,
                    isDeleted = user.isDeleted,
                    createdAt = user.createdAt
                )
            }
        )
    }

    @PreAuthorize("@adminPermissionService.hasAdminRole()")
    @PostMapping("/users/{userId}/toggle-admin")
    fun toggleAdminStatus(
        @AuthenticationPrincipal myfarmAuth: MyfarmAuthentication,
        @PathVariable userId: UUID
    ): ToggleAdminStatus.Response {
        val user = usersRepository.findById(userId)
            ?: throw IllegalArgumentException("사용자를 찾을 수 없습니다")
        
        val updatedUser = user.copy(isAdmin = !user.isAdmin)
        usersRepository.save(updatedUser)
        
        return ToggleAdminStatus.Response(
            message = "사용자 관리자 권한이 변경되었습니다",
            userId = userId,
            isAdmin = updatedUser.isAdmin
        )
    }
}