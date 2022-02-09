package de.mo.coding.webshop.repository

import de.mo.coding.webshop.model.CustomerResponse

class CustomerRepository {

    private val customers = listOf(
            CustomerResponse(
                    id = "1",
                    firstName = "Mojtaba",
                    lastName = "Bakhtiari",
                    email = "test@gmail.com"
            )
    )


    fun findById(id: String): CustomerResponse? {
        return customers.find { it.id == id }
    }
}