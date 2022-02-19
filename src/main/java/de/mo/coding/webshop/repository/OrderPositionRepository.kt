package de.mo.coding.webshop.repository

import javax.persistence.Embeddable

@Embeddable
class OrderPositionEntity(
        val id: String,
        val productId: String,
        val quantity: Long
)
