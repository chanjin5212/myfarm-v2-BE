package com.myfarm.myfarm.adapter.out.persistence.users

import com.myfarm.myfarm.domain.users.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UsersJpaRepository : JpaRepository<Users, UUID> {
    fun findByLoginId(loginId: String): Users

    fun existsByEmail(email: String): Boolean

    fun existsByLoginId(loginId: String): Boolean

    fun findByEmail(email: String): Users?

    fun existsByNickname(nickname: String): Boolean

    fun findByEmailAndLoginId(email: String, loginId: String): Users?
}
