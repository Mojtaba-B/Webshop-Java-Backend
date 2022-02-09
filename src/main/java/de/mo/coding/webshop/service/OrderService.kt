package de.mo.coding.webshop.service

import de.mo.coding.webshop.model.OrderCreateRequest
import de.mo.coding.webshop.model.OrderPositionCreateRequest
import de.mo.coding.webshop.model.OrderPositionResponse
import de.mo.coding.webshop.model.OrderResponse
import de.mo.coding.webshop.repository.CustomerRepository
import de.mo.coding.webshop.repository.OrderPositionRepository
import de.mo.coding.webshop.repository.OrderRepository
import de.mo.coding.webshop.repository.ProductRepository
import java.util.*

class OrderService {
    private val orderRepository: OrderRepository = OrderRepository()
    private val customerRepository: CustomerRepository = CustomerRepository()
    private val productRepository: ProductRepository = ProductRepository()
    private val orderPositionRepository: OrderPositionRepository = OrderPositionRepository()

    fun createOrder(request: OrderCreateRequest): OrderResponse {
        customerRepository.findById(request.customerId) ?: throw Exception("Customer not found")
        return orderRepository.sava(request)
    }

    fun createNewPositionsForOrder(
            orderId: String,
            request: OrderPositionCreateRequest): OrderPositionResponse {

        orderRepository.findById(orderId) ?: throw Exception("order not found")
        productRepository.findById(request.productId) ?: throw Exception("product not found")

        val orderPositionResponse = OrderPositionResponse(
                id = UUID.randomUUID().toString(),
                productId = request.productId,
                quantity = request.quantity
        )

        orderPositionRepository.save(orderPositionResponse)
        return orderPositionResponse
    }
}
