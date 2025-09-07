package com.myfarm.myfarm.adapter.`in`.web.carriers.message

import com.myfarm.myfarm.adapter.out.external.deliverytracker.message.CarrierResponse

class ListCarrier {
    data class Response(
        val success: Boolean,
        val carriers: List<CarrierResponse>
    )
}