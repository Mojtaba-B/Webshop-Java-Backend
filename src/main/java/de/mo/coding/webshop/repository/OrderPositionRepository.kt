package de.mo.coding.webshop.repository

import de.mo.coding.webshop.model.OrderPositionResponse

class OrderPositionRepository {
    private val orderPositions = mutableListOf<OrderPositionResponse>()
    fun save(orderPositionResponse: OrderPositionResponse) {
        orderPositions.add(orderPositionResponse)
    }

}
