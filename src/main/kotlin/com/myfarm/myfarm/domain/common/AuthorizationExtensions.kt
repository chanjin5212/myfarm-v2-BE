package com.myfarm.myfarm.domain.common

import com.myfarm.myfarm.adapter.`in`.web.config.security.MyfarmAuthentication
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

/**
 * MyfarmAuthentication에서 관리자 권한을 체크하는 확장함수
 */
fun MyfarmAuthentication?.checkAdminPermission(): MyfarmAuthentication {
    if (this == null) {
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증이 필요합니다.")
    }

    val hasAdminRole = this.authorities?.any {
        it?.authority == "ROLE_ADMIN"
    } ?: false

    if (!hasAdminRole) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.")
    }

    return this
}

/**
 * 소유권을 체크하는 확장함수
 */
fun <T : Authorizable> T?.checkOwnership(
    userId: UUID
): T {
    if (this == null) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다.")
    }

    if (this.getOwnerId() != userId) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.")
    }

    return this
}

fun <T : Authorizable> List<T>.checkAllOwnership(
    userId: UUID
): List<T> {
    if (this.isEmpty()) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다.")
    }

    if (this.any { it.getOwnerId() != userId }) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.")
    }

    return this
}