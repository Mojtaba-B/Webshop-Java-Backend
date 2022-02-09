package de.mo.coding.webshop.model

data class OrderPositionCreateRequest(
        val productId: String,
        val quantity: Long
)
