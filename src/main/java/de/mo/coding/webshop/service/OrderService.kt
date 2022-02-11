package de.mo.coding.webshop.service

import de.mo.coding.webshop.exceptions.WebShopException
import de.mo.coding.webshop.model.OrderCreateRequest
import de.mo.coding.webshop.model.OrderPositionCreateRequest
import de.mo.coding.webshop.model.OrderPositionResponse
import de.mo.coding.webshop.model.OrderResponse
import de.mo.coding.webshop.repository.CustomerRepository
import de.mo.coding.webshop.repository.OrderPositionRepository
import de.mo.coding.webshop.repository.OrderRepository
import de.mo.coding.webshop.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
        private val orderRepository: OrderRepository,
        private val customerRepository: CustomerRepository,
        private val productRepository: ProductRepository,
        private val orderPositionRepository: OrderPositionRepository
) {


    fun createOrder(request: OrderCreateRequest): OrderResponse {
        customerRepository.findById(request.customerId) ?: throw WebShopException(
                message = "Customer with id ${request.customerId} not found", statusCode = HttpStatus.BAD_REQUEST)

        return orderRepository.sava(request)
    }

    fun createNewPositionsForOrder(
            orderId: String,
            request: OrderPositionCreateRequest): OrderPositionResponse {

        orderRepository.findById(orderId) ?: throw WebShopException(
                message = "order with id $orderId not found", statusCode = HttpStatus.BAD_REQUEST)

        productRepository.findById(request.productId) ?: throw WebShopException(
                message = "product with id ${request.productId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val orderPositionResponse = OrderPositionResponse(
                id = UUID.randomUUID().toString(),
                orderId = orderId,
                productId = request.productId,
                quantity = request.quantity
        )

        orderPositionRepository.save(orderPositionResponse)
        return orderPositionResponse
    }
}
