package com.doni.kotlinrestreturnman.model

class GetReturnOrderResponse (
            val refundAmount: Double,
            val status: ReturnOrderStatus,
            val items: List<GetItemsResponse>
)