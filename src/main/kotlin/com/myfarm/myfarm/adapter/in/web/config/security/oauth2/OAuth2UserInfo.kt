package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

interface OAuth2UserInfo {
    val id: String
    val name: String
    val email: String
    val phoneNumber: String?
    val imageUrl: String?
}
