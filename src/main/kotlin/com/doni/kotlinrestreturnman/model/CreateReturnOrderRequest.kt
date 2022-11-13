package com.doni.kotlinrestreturnman.model

data class CreateReturnOrderRequest (
        val orderId: String,
        val emailAddress: String,
        val token: String,
        val items: List<Int>?
)
