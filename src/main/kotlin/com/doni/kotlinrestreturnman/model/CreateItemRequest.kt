package com.doni.kotlinrestreturnman.model

data class CreateItemRequest (

        val sku: String,

        val quantity: Int,

        val price: Int,

        val itemName: String,
)
