package com.myfarm.myfarm.adapter.out.external.deliverytracker

import com.myfarm.myfarm.adapter.out.external.deliverytracker.message.TrackingResponse
import com.myfarm.myfarm.adapter.out.external.deliverytracker.message.CarrierResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DeliveryTrackerClient(
    private val webClient: WebClient
) {
    companion object {
        private const val BASE_URL = "https://apis.tracker.delivery"
    }

    fun getTrackingInfo(carrierId: String, trackingNumber: String): Mono<TrackingResponse> {
        return webClient.get()
            .uri("$BASE_URL/carriers/{carrierId}/tracks/{trackingNumber}", carrierId, trackingNumber)
            .retrieve()
            .bodyToMono(TrackingResponse::class.java)
    }

    fun getCarriers(): Mono<List<CarrierResponse>> {
        return webClient.get()
            .uri("$BASE_URL/carriers")
            .retrieve()
            .bodyToMono(Array<CarrierResponse>::class.java)
            .map { it.toList() }
    }
}