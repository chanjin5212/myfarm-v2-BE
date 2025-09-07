package com.myfarm.myfarm.adapter.out.external.deliverytracker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class DeliveryTrackerClientConfig {

    @Bean
    fun deliveryTrackerWebClient(): WebClient {
        return WebClient.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(1024 * 1024)
            }
            .build()
    }
}