package com.doni.kotlinrestreturnman.service

import com.doni.kotlinrestreturnman.model.CreateReturnOrderRequest
import com.doni.kotlinrestreturnman.model.ReturnOrderResponse

interface ReturnOrderService {
    fun createReturnOrder(createReturnOrderRequest: CreateReturnOrderRequest): ReturnOrderResponse
}