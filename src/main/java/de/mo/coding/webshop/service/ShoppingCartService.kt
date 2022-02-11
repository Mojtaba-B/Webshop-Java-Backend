package de.mo.coding.webshop.service

import de.mo.coding.webshop.exceptions.IdNotFoundException
import de.mo.coding.webshop.model.OrderPositionResponse
import de.mo.coding.webshop.model.OrderResponse
import de.mo.coding.webshop.model.ShoppingCartResponse
import de.mo.coding.webshop.repository.OrderPositionRepository
import de.mo.coding.webshop.repository.OrderRepository
import de.mo.coding.webshop.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ShoppingCartService(
        private val orderRepository: OrderRepository,
        private val orderPositionRepository: OrderPositionRepository,
        private val productRepository: ProductRepository

) {
    fun getShoppingCartForCustomer(customerId: String): ShoppingCartResponse {

        val orders: List<OrderResponse> = orderRepository.findAllByCustomerIdWhereOrderStatusIsNew(customerId)
        val orderIds = orders.map { it.id }
        val orderPositions: List<OrderPositionResponse> = orderPositionRepository.findAllByOrderIds(orderIds)

        val deliveryCost = 800L; //TODO: feature to select delivery method?
        val totalAmount = calculateSumForCart(orderPositions, deliveryCost)

        return ShoppingCartResponse(
                customerId = customerId,
                orderPositions = orderPositions,
                deliveryOption = "STANDARD",
                deliveryCostInCent = deliveryCost,
                totalAmountInCent = totalAmount
        )
    }

     fun calculateSumForCart(orderPositions: List<OrderPositionResponse>, deliveryCost: Long): Long {
         val positionAmounts: List<Long> = orderPositions.map {
             val product = productRepository.findById(it.productId)
                     .orElseThrow { throw IdNotFoundException("product with id ${it.productId} not found") }
             if (it.quantity <= 0) {
                 throw IllegalArgumentException("OrderPosition with quantity of ${it.quantity} is not allowed")
             }
             it.quantity * product.priceInCent
         }

         val positionsSum = positionAmounts.sumOf { it }
        return positionsSum + deliveryCost
    }
}
