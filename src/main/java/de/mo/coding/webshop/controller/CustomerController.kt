package de.mo.coding.webshop.controller

import de.mo.coding.webshop.model.CustomerResponse
import de.mo.coding.webshop.repository.CustomerRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class CustomerController {
    private val customerRepository: CustomerRepository = CustomerRepository()

    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: String): ResponseEntity<CustomerResponse> {

        val customer = customerRepository.findById(id)
        return if (customer != null)
            ResponseEntity.ok(customer)
        else
            ResponseEntity.notFound().build()
    }
}