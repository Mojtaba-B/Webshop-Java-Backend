package de.mo.coding.webshop.repository


import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

interface CustomerRepository:JpaRepository<CustomerEntity, String>{}

@Entity
@Table(name="customers")
class CustomerEntity(
        @Id val id: String,
        val firstName: String,
        val lastName: String,
        val email: String
)
