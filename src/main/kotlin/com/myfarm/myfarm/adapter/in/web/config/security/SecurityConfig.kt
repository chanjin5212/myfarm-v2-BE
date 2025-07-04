package com.myfarm.myfarm.adapter.`in`.web.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.myfarm.myfarm.adapter.`in`.web.config.security.authentication.MyfarmAuthenticationFilter
import com.myfarm.myfarm.adapter.`in`.web.config.security.authentication.MyfarmDevAuthorizationFilter
import com.myfarm.myfarm.adapter.`in`.web.config.security.authentication.MyfarmUserDetailsService
import com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2.CustomOAuth2UserService
import com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2.OAuth2AuthenticationFailureHandler
import com.myfarm.myfarm.adapter.`in`.web.config.security.oauth2.OAuth2AuthenticationSuccessHandler
import com.myfarm.myfarm.domain.users.port.UsersRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val usersRepository: UsersRepository,
    private val objectMapper: ObjectMapper,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun securityContextRepository(): SecurityContextRepository {
        return HttpSessionSecurityContextRepository()
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        environment: Environment,
        corsConfigurationSource: CorsConfigurationSource,
        myfarmUserDetailsService: MyfarmUserDetailsService,
        authenticationManager: AuthenticationManager,
        securityContextRepository: SecurityContextRepository
    ): SecurityFilterChain {
        val httpSecurity = http
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource)
            }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                    .maximumSessions(1)
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/api/email-verifications/v1/send",
                        "/api/email-verifications/v1/verify",
                        "/api/users/v1/register",
                        "/api/users/v1/login",
                        "/api/users/v1/check-duplicate"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2
                    .authorizationEndpoint { authorization ->
                        authorization.baseUri("/oauth2/authorize")
                    }
                    .redirectionEndpoint { redirection ->
                        redirection.baseUri("/login/oauth2/code/*")
                    }
                    .userInfoEndpoint { userInfo ->
                        userInfo.userService(customOAuth2UserService)
                    }
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
            }
            .logout {
                it
                    .logoutUrl("/api/users/v1/logout")
                    .logoutSuccessHandler { request, response, _ ->
                        request.session.invalidate()
                        response.status = 200
                    }
            }
            .userDetailsService(myfarmUserDetailsService)
            .addFilterBefore(
                MyfarmAuthenticationFilter(
                    securityContextRepository,
                    authenticationManager,
                    objectMapper
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )

        if (environment.matchesProfiles("local")) {
            http.addFilterBefore(
                MyfarmDevAuthorizationFilter(usersRepository),
                AuthorizationFilter::class.java
            )
        }

        return httpSecurity.build()
    }
}
