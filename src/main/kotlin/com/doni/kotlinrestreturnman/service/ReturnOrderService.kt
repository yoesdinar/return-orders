package com.doni.kotlinrestreturnman.service

import com.doni.kotlinrestreturnman.model.*

interface ReturnOrderService {
    fun createReturnOrder(createReturnOrderRequest: CreateReturnOrderRequest): CreateReturnOrderResponse
    fun pendingReturn(pendingRequest: PendingRequest): PendingResponse
    fun getReturnOrder(id: Long): GetReturnOrderResponse
    fun updateQCStatus(returnOrderId: Long, itemId: Int, status: QcItemStatus): String
}