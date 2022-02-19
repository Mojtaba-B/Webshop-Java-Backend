package de.mo.coding.webshop.repository

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


interface OrderPositionRepository : JpaRepository<OrderPositionEntity, String>

@Entity
@Table(name = "order_positions")
class OrderPositionEntity(
        @Id val id: String,
        val orderId: String,
        val productId: String,
        val quantity: Long
)
