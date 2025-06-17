package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

class NaverOAuth2UserInfo(
    private val attributes: Map<String, Any>
) : OAuth2UserInfo {

    override val id: String
        get() {
            val response = getResponseMap()
            return response?.get("id") as? String
                ?: throw IllegalArgumentException("네이버 응답에 ID 정보가 없습니다")
        }

    override val name: String
        get() {
            val response = getResponseMap()
            return response?.get("name") as? String ?: "Unknown"
        }

    override val email: String
        get() {
            val response = getResponseMap()
            return response?.get("email") as? String
                ?: throw IllegalArgumentException("네이버 계정에 이메일 정보가 없습니다")
        }

    override val phoneNumber: String?
        get() {
            val response = getResponseMap()
            return response?.get("mobile") as? String
        }

    override val imageUrl: String?
        get() {
            val response = getResponseMap()
            return response?.get("profile_image") as? String
        }

    @Suppress("UNCHECKED_CAST")
    private fun getResponseMap(): Map<String, Any>? {
        return attributes["response"] as? Map<String, Any>
    }
}
