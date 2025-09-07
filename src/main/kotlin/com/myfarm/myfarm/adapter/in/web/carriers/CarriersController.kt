package com.myfarm.myfarm.adapter.`in`.web.carriers

import com.myfarm.myfarm.adapter.`in`.web.carriers.message.ListCarrier
import com.myfarm.myfarm.adapter.out.external.deliverytracker.DeliveryTrackerClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/carriers/v1")
class CarriersController(
    private val deliveryTrackerClient: DeliveryTrackerClient
) {

    @GetMapping
    fun listCarriers(): ListCarrier.Response {
        val carriers = deliveryTrackerClient.getCarriers().block()
            ?: throw IllegalArgumentException("택배사 정보를 가져올 수 없습니다")

        return ListCarrier.Response(
            success = true,
            carriers = carriers
        )
    }
}