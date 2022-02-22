package de.mo.coding.webshop.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    lateinit var repository: CustomerRepository

    @Test
    fun `find all on empty db - returns empty list`() {
        // given

        // when
        val result: List<CustomerEntity> = repository.findAll()

        // then
        assertThat(result).isEmpty()
    }

    @Test
    fun `find all on db after saving customer - returns list with customer`() {
        // given
        val customerEntity = CustomerEntity(
                id = "123",
                firstName = "Mo",
                lastName = "Bakhtiari",
                salutation = "Mr.",
                email = "mo@test.com",
        )
        repository.save(customerEntity)

        // when
        val result: List<CustomerEntity> = repository.findAll()

        // then
        assertThat(result).containsExactly(customerEntity)
    }

}