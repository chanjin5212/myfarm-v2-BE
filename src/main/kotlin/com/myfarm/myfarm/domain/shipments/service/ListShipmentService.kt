package com.myfarm.myfarm.domain.shipments.service

import com.myfarm.myfarm.adapter.`in`.web.shipments.message.ListShipment
import com.myfarm.myfarm.adapter.out.persistence.shipments.ShipmentsAdapter
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListShipmentService(
    private val shipmentsAdapter: ShipmentsAdapter
) {

    @Transactional(readOnly = true)
    fun listShipment(request: ListShipment.Request): ListShipment.Response {
        val pageable = PageRequest.of(request.page, request.size)

        val shipmentPage = shipmentsAdapter.search(
            status = request.status,
            carrier = request.carrier,
            pageable = pageable
        )

        val shipmentInfoList = shipmentPage.content.map { shipment ->
            ListShipment.ShipmentInfo(
                id = shipment.id,
                orderId = shipment.orderId,
                trackingNumber = shipment.trackingNumber,
                carrier = shipment.carrier,
                status = shipment.status,
                shippedAt = shipment.shippedAt,
                deliveredAt = shipment.deliveredAt,
                carrierName = shipment.carrierName,
                createdAt = shipment.createdAt,
                updatedAt = shipment.updatedAt
            )
        }

        return ListShipment.Response(
            totalCount = shipmentPage.totalElements.toInt(),
            shipment = shipmentInfoList
        )
    }
}
