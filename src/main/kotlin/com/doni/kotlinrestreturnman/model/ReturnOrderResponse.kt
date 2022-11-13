package com.doni.kotlinrestreturnman.model

import com.doni.kotlinrestreturnman.entity.Item

class ReturnOrderResponse (
            val returnOrderId: Long,
            val refundAmount: Int
)