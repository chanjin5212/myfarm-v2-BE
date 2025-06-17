package com.myfarm.myfarm.domain.common

import java.util.UUID

interface Authorizable {
    fun getOwnerId(): UUID
}
