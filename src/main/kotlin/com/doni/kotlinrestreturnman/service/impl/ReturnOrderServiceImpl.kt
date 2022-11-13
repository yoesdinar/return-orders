package com.doni.kotlinrestreturnman.service.impl

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.entity.Order
import com.doni.kotlinrestreturnman.entity.ReturnOrder
import com.doni.kotlinrestreturnman.model.CreateReturnOrderRequest
import com.doni.kotlinrestreturnman.model.ReturnOrderResponse
import com.doni.kotlinrestreturnman.repository.ItemRepository
import com.doni.kotlinrestreturnman.repository.OrderRepository
import com.doni.kotlinrestreturnman.repository.ReturnOrderRepository
import com.doni.kotlinrestreturnman.service.ReturnOrderService
import org.springframework.stereotype.Service

@Service
class ReturnOrderServiceImpl(
        val returnOrderRepository: ReturnOrderRepository,
        ): ReturnOrderService {
    override fun createReturnOrder(createReturnOrderRequest: CreateReturnOrderRequest): ReturnOrderResponse {
        val orderEntity = Order(
                orderId = createReturnOrderRequest.orderId,
                emailAddress = createReturnOrderRequest.emailAddress,
        )

        val itemsEntity = createReturnOrderRequest.items.map {
            Item(
                    sku = it.sku,
                    quantity = it.quantity,
                    price = it.price,
                    itemName = it.itemName,
                    order = orderEntity
            )
        }

        val returnOrder = ReturnOrder(
                order = orderEntity,
                items = itemsEntity
        )

        returnOrderRepository.save(returnOrder)

        return ReturnOrderResponse(
                code = 200,
                returnOrderId = returnOrder.id,
                refundAmount = 200000,
                items = listOf()
        )
    }

}