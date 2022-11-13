package com.doni.kotlinrestreturnman.model

import com.doni.kotlinrestreturnman.entity.Item

class ReturnOrderResponse (
            val code: Int ,
            val returnOrderId: Long,
            val refundAmount: Int,
            val items: List<Item>
)