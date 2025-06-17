package com.myfarm.myfarm.adapter.`in`.web.users.message

import java.util.UUID

abstract class Me {

    data class Response(
        val id: UUID,
        val email: String?
    )
}
