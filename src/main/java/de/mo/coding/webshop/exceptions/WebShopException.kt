package de.mo.coding.webshop.exceptions

import org.springframework.http.HttpStatus

data class WebShopException(
        override val message: String,
        val statusCode: HttpStatus

) : RuntimeException(message)


data class IdNotFoundException(
        override val message: String,
        val statusCode: HttpStatus = HttpStatus.BAD_REQUEST

) : RuntimeException(message)