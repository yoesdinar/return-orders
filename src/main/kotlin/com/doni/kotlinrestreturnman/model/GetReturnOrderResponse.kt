package com.doni.kotlinrestreturnman.model

class GetReturnOrderResponse (
            val refundAmount: Int,
            val status: ReturnOrderStatus,
            val items: List<GetItemsResponse>
)