package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

object OAuth2UserInfoFactory {

    fun createOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
        return when (registrationId.lowercase()) {
            "kakao" -> KakaoOAuth2UserInfo(attributes)
            "naver" -> NaverOAuth2UserInfo(attributes)
            else -> throw IllegalArgumentException("지원하지 않는 OAuth2 제공자입니다: $registrationId")
        }
    }
}
