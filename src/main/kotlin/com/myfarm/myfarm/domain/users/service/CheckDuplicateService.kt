package com.myfarm.myfarm.domain.users.service

import com.myfarm.myfarm.adapter.`in`.web.users.message.CheckDuplicate
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.stereotype.Service

@Service
class CheckDuplicateService(
    private val usersRepository: UsersRepository
) {

    fun checkDuplicate(request: CheckDuplicate.Request): CheckDuplicate.Response {
        val isDuplicate = when (request.type) {
            "email" -> {
                if (request.value.isBlank()) {
                    throw IllegalArgumentException("이메일을 입력해주세요")
                }
                usersRepository.existsByEmail(request.value)
            }
            "login_id" -> {
                if (request.value.isBlank()) {
                    throw IllegalArgumentException("로그인 ID를 입력해주세요")
                }
                usersRepository.existsByLoginId(request.value)
            }
            "nickname" -> {
                if (request.value.isBlank()) {
                    throw IllegalArgumentException("닉네임을 입력해주세요")
                }
                usersRepository.existsByNickname(request.value)
            }
            else -> throw IllegalArgumentException("지원하지 않는 중복 확인 타입입니다: ${request.type}")
        }

        return if (isDuplicate) {
            CheckDuplicate.Response(
                available = false,
                message = when (request.type) {
                    "email" -> "이미 사용 중인 이메일입니다"
                    "login_id" -> "이미 사용 중인 로그인 ID입니다"
                    "nickname" -> "이미 사용 중인 닉네임입니다"
                    else -> "이미 사용 중입니다"
                }
            )
        } else {
            CheckDuplicate.Response(
                available = true,
                message = when (request.type) {
                    "email" -> "사용 가능한 이메일입니다"
                    "login_id" -> "사용 가능한 로그인 ID입니다"
                    "nickname" -> "사용 가능한 닉네임입니다"
                    else -> "사용 가능합니다"
                }
            )
        }
    }
}
