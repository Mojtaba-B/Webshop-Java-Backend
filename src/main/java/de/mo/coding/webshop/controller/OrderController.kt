package de.mo.coding.webshop.controller

import de.mo.coding.webshop.model.OrderCreateRequest
import de.mo.coding.webshop.model.OrderPositionCreateRequest
import de.mo.coding.webshop.model.OrderResponse
import de.mo.coding.webshop.service.OrderService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
        private val orderService: OrderService
) {

    @PostMapping("/orders")
    fun createOrder(
            @RequestBody request: OrderCreateRequest
    ): OrderResponse {
        return orderService.createOrder(request)

    }

    @PostMapping("/orders/{id}/positions")
    fun createPosition(
            @PathVariable(name = "id") orderId: String,
            @RequestBody request: OrderPositionCreateRequest
    ) {
        orderService.createNewPositionsForOrder(orderId, request)
    }
}