package de.mo.coding.webshop.service

import de.mo.coding.webshop.entity.OrderEntity
import de.mo.coding.webshop.exceptions.WebShopException
import de.mo.coding.webshop.model.*
import de.mo.coding.webshop.repository.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class OrderService(
        private val orderRepository: OrderRepository,
        private val customerRepository: CustomerRepository,
        private val productRepository: ProductRepository
) {


    fun createOrder(request: OrderCreateRequest): OrderResponse {
        customerRepository.findById(request.customerId) ?: throw WebShopException(
                message = "Customer with id ${request.customerId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val order = OrderEntity(
                id = UUID.randomUUID().toString(),
                customerId = request.customerId,
                orderTime = LocalDateTime.now(),
                status = OrderStatus.NEW,
                orderPositions = emptyList()
        )

        val savedOrder = orderRepository.save(order)
        return mapToResponse(savedOrder)
    }

    fun createNewPositionsForOrder(
            orderId: String,
            request: OrderPositionCreateRequest): OrderPositionResponse {

        val order: OrderEntity = orderRepository.getOne(orderId)

        productRepository.findById(request.productId) ?: throw WebShopException(
                message = "product with id ${request.productId} not found", statusCode = HttpStatus.BAD_REQUEST)

        val orderPosition = OrderPositionEntity(
                id = UUID.randomUUID().toString(),
                productId = request.productId,
                quantity = request.quantity
        )

        val updatedOrderPositions = order.orderPositions.plus(orderPosition)
        val updatedOrder = order.copy(
                orderPositions = updatedOrderPositions
        )
        orderRepository.save(updatedOrder)

        return mapToResponse(orderPosition)
    }


    fun updateOrder(id: String, request: OrderUpdateRequest): OrderResponse {
        //val order = orderRepository.findById(id) ?: throw IdNotFoundException("Order with id $id not found")
        val order = orderRepository.getOne(id)
        val updateOrder = order.copy(
                status = request.orderStatus ?: order.status
        )

        val savedOrder = orderRepository.save(updateOrder)
        return mapToResponse(savedOrder)
    }

    private fun mapToResponse(savedOrder: OrderEntity) =
            OrderResponse(
                    savedOrder.id,
                    savedOrder.customerId,
                    savedOrder.orderTime,
                    savedOrder.status,
                    emptyList()
            )

    fun getOrder(id: String): GetOrderResponse {
        val order = orderRepository.getOne(id)

        val customer = customerRepository.getOne(order.customerId)

        val positions = order.orderPositions
                .map {
                    val product = productRepository.getOne(it.productId)
                    GetOrderPositionResponse(
                            id = it.id,
                            quantity = it.quantity,
                            product = ProductResponse(
                                    product.id,
                                    product.name,
                                    product.description,
                                    product.priceInCent,
                                    product.tags
                            )
                    )
                }
        return GetOrderResponse(
                id = order.id,
                orderTime = order.orderTime,
                status = order.status,
                customer = CustomerResponse(
                        id = customer.id,
                        firstName = customer.firstName,
                        lastName = customer.lastName,
                        email = customer.email
                ),
                orderPositions = positions
        )
    }

    companion object {
        fun mapToResponse(savedOrderPosition: OrderPositionEntity): OrderPositionResponse {
            return OrderPositionResponse(
                    id = savedOrderPosition.id,
                    productId = savedOrderPosition.productId,
                    quantity = savedOrderPosition.quantity

            )
        }

    }
}
