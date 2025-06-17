package com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2

class KakaoOAuth2UserInfo(
    private val attributes: Map<String, Any>
) : OAuth2UserInfo {

    override val id: String = attributes["id"].toString()

    override val name: String
        get() {
            val properties = getPropertiesMap()
            return properties?.get("nickname") as? String ?: "Unknown"
        }

    override val email: String
        get() {
            val kakaoAccount = getKakaoAccountMap()
            return kakaoAccount?.get("email") as? String
                ?: throw IllegalArgumentException("카카오 계정에 이메일 정보가 없습니다")
        }

    override val phoneNumber: String?
        get() {
            val kakaoAccount = getKakaoAccountMap()
            return kakaoAccount?.get("phone_number") as? String
        }

    override val imageUrl: String?
        get() {
            val properties = getPropertiesMap()
            return properties?.get("profile_image") as? String
        }

    @Suppress("UNCHECKED_CAST")
    private fun getPropertiesMap(): Map<String, Any>? {
        return attributes["properties"] as? Map<String, Any>
    }

    @Suppress("UNCHECKED_CAST")
    private fun getKakaoAccountMap(): Map<String, Any>? {
        return attributes["kakao_account"] as? Map<String, Any>
    }
}
