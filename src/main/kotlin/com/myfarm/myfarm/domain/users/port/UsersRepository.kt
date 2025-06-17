package com.myfarm.myfarm.domain.users.port

import com.myfarm.myfarm.adapter.out.persistence.users.UsersJpaRepository
import com.myfarm.myfarm.domain.users.entity.Users
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class UsersRepository(
    private val usersJpaRepository: UsersJpaRepository
) {
    fun findById(id: UUID): Users? {
        return usersJpaRepository.findById(id).getOrNull()
    }

    fun findByLoginId(loginId: String): Users? {
        return usersJpaRepository.findByLoginId(loginId)
    }

    fun existsByEmail(email: String): Boolean {
        return usersJpaRepository.existsByEmail(email)
    }

    fun existsByLoginId(loginId: String): Boolean {
        return usersJpaRepository.existsByLoginId(loginId)
    }

    fun save(user: Users): Users {
        return usersJpaRepository.save(user)
    }

    fun findByEmail(email: String): Users? {
        return usersJpaRepository.findByEmail(email)
    }
}
