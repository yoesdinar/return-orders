package com.doni.kotlinrestreturnman.model

class GetItemsResponse (
            val itemId: Int,
            val quantity: Int,
            val qcStatus: QcItemStatus,
            val price: Double,
            val itemName: String,
            val sku: String
)
