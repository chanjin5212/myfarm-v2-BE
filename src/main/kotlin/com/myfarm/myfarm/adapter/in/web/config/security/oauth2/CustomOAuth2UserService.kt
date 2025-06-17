package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

import com.myfarm.myfarm.domain.users.service.OAuth2UserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val oAuth2UserService: OAuth2UserService
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId

        return processOAuth2User(registrationId, oAuth2User)
    }

    private fun processOAuth2User(registrationId: String, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = OAuth2UserInfoFactory.createOAuth2UserInfo(
            registrationId,
            oAuth2User.attributes
        )

        val user = oAuth2UserService.processOAuth2User(registrationId, oAuth2UserInfo)

        return MyfarmOAuth2User.create(user, oAuth2User.attributes)
    }
}
