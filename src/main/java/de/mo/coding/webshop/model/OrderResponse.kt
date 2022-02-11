package de.mo.coding.webshop.model

import java.time.LocalDateTime

data class OrderResponse(
        val id: String,
        val customerId: String,
        val orderTime: LocalDateTime,
        val status: OrderStatus,
        val orderPosition: List<OrderPositionResponse>
)

data class OrderPositionResponse(
        val id: String,
        val orderId: String,
        val productId: String,
        val quantity: Long
)


enum class OrderStatus {
    NEW, CONFIRMED, SENT, DELIVERED, CANCELED
}
