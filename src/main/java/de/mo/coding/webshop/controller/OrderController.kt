package de.mo.coding.webshop.controller

import de.mo.coding.webshop.model.*
import de.mo.coding.webshop.service.OrderService
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/orders/{id}")
    fun updateOrder(
            @PathVariable id: String,
            @RequestBody request: OrderUpdateRequest
    ): OrderResponse {
        return orderService.updateOrder(id, request)
    }

    @GetMapping("/orders/{id}")
    fun getOrder(@PathVariable id:String):GetOrderResponse{
        return orderService.getOrder(id)
    }

}