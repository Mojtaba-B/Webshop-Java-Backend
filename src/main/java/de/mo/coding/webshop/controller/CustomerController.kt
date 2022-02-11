package de.mo.coding.webshop.controller

import de.mo.coding.webshop.model.CustomerResponse
import de.mo.coding.webshop.model.ShoppingCartResponse
import de.mo.coding.webshop.repository.CustomerRepository
import de.mo.coding.webshop.service.ShoppingCartService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class CustomerController(
        private val customerRepository: CustomerRepository,
        private val shoppingCartService: ShoppingCartService
) {


    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: String): CustomerResponse? {
        return customerRepository.findById(id)
    }


    @GetMapping("/customers/{id}/shoppingcart")
    fun getShoppingCartByCustomerId(
            @PathVariable id: String
    ): ShoppingCartResponse {

        return shoppingCartService.getShoppingCartForCustomer(id)


    }
}