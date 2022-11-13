package com.doni.kotlinrestreturnman.service.impl

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.entity.ReturnOrder
import com.doni.kotlinrestreturnman.error.NotFoundException
import com.doni.kotlinrestreturnman.model.CreateReturnOrderRequest
import com.doni.kotlinrestreturnman.model.ReturnOrderResponse
import com.doni.kotlinrestreturnman.repository.ItemRepository
import com.doni.kotlinrestreturnman.repository.OrderRepository
import com.doni.kotlinrestreturnman.repository.ReturnOrderRepository
import com.doni.kotlinrestreturnman.service.ReturnOrderService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReturnOrderServiceImpl(
        val returnOrderRepository: ReturnOrderRepository,
        val orderRepository: OrderRepository,
        val itemRepository: ItemRepository
) : ReturnOrderService {
    override fun createReturnOrder(createReturnOrderRequest: CreateReturnOrderRequest): ReturnOrderResponse {
        val orderEntity = orderRepository.findByIdOrNull(createReturnOrderRequest.orderId) ?: throw NotFoundException()

        var itemsEntity = itemRepository.findByOrderId(createReturnOrderRequest.orderId)

        if (!createReturnOrderRequest.items.isNullOrEmpty()) {
            itemsEntity = filterOrderItemsByItemIds(items = itemsEntity, itemIds = createReturnOrderRequest.items)
        }

        val returnOrder = ReturnOrder(
                order = orderEntity,
                items = itemsEntity
        )

        returnOrderRepository.save(returnOrder)

        return ReturnOrderResponse(
                returnOrderId = returnOrder.id,
                refundAmount = calculateRefundAmount(returnOrder.items),
        )
    }

    private fun filterOrderItemsByItemIds(items: List<Item>, itemIds: List<Int>): List<Item> {
        return items.filter { item ->
            itemIds.any { it == item.itemId }
        }
    }

    private fun calculateRefundAmount(items: List<Item>): Int {
        return items.sumOf { it.price }
    }
}
