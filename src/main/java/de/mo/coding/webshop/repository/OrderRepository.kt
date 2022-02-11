package de.mo.coding.webshop.repository

import de.mo.coding.webshop.model.OrderCreateRequest
import de.mo.coding.webshop.model.OrderResponse
import de.mo.coding.webshop.model.OrderStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class OrderRepository {

    private val orders = mutableListOf<OrderResponse>()

    fun sava(request: OrderCreateRequest): OrderResponse {
        val orderResponse = OrderResponse(
                id = UUID.randomUUID().toString(),
                customerId = request.customerId,
                orderTime = LocalDateTime.now(),
                status = OrderStatus.NEW,
                orderPosition = emptyList()
        )
        orders.add(orderResponse)
        return orderResponse

    }

    fun findById(orderId: String): OrderResponse? {
        return orders.find { it.id == orderId }
    }

}