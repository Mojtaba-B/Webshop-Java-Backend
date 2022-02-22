package de.mo.coding.webshop.entity

import javax.persistence.Embeddable

@Embeddable
class OrderPositionEntity(
        val id: String,
        val productId: String,
        val quantity: Long
)
