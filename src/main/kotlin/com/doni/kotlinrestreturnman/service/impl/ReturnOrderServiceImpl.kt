package com.doni.kotlinrestreturnman.service.impl

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.entity.ReturnOrder
import com.doni.kotlinrestreturnman.error.ItemToReturnNotFoundException
import com.doni.kotlinrestreturnman.error.OrderNotFoundException
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
        val orderEntity = orderRepository.findByIdOrNull(createReturnOrderRequest.orderId) ?: throw OrderNotFoundException()

        var itemsEntity = itemRepository.findByOrderIdAndQcStatusNotAndReturnOrderIdIsNull(orderEntity.orderId)

        if (!createReturnOrderRequest.items.isNullOrEmpty()) {
            itemsEntity = filterOrderItemsByItemIds(items = itemsEntity, itemIds = createReturnOrderRequest.items)
        }

        if (itemsEntity.isNullOrEmpty()) {
            throw ItemToReturnNotFoundException()
        }

        val returnOrder = ReturnOrder(
                order = orderEntity,
                items = itemsEntity
        )

        returnOrder.order = returnOrder.order.apply { this.returnOrder = returnOrder }
        returnOrder.items = injectItemsByReturnOrder(returnOrder.items, returnOrder)

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

    private fun injectItemsByReturnOrder(items: List<Item>, returnOrder: ReturnOrder): List<Item> {
        return items.map {
            it.apply { it.returnOrder = returnOrder }
        }
    }

    private fun calculateRefundAmount(items: List<Item>): Int {
        return items.sumOf { it.price }
    }
}
