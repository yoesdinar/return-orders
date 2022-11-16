package com.doni.kotlinrestreturnman.service.impl

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.entity.ReturnOrder
import com.doni.kotlinrestreturnman.error.ItemToReturnNotFoundException
import com.doni.kotlinrestreturnman.error.OrderNotFoundException
import com.doni.kotlinrestreturnman.error.ReturnOrderNotFoundException
import com.doni.kotlinrestreturnman.error.UnauthorizedException
import com.doni.kotlinrestreturnman.model.*
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
    override fun createReturnOrder(createReturnOrderRequest: CreateReturnOrderRequest): CreateReturnOrderResponse {
        if (createReturnOrderRequest.token.isNullOrEmpty() || createReturnOrderRequest.token != "DEFAULT_TOKEN") {
            throw UnauthorizedException()
        }

        var itemsEntity = itemRepository.findByOrderOrderIdAndReturnOrderIdIsNull(createReturnOrderRequest.orderId)

        if (!createReturnOrderRequest.items.isNullOrEmpty()) {
            itemsEntity = filterOrderItemsByItemIds(items = itemsEntity, itemIds = createReturnOrderRequest.items)
        }

        if (itemsEntity.isNullOrEmpty()) {
            throw ItemToReturnNotFoundException()
        }

        val returnOrder = ReturnOrder(
                items = itemsEntity
        )

        returnOrder.items = injectItemsByReturnOrder(returnOrder.items, returnOrder)

        returnOrderRepository.save(returnOrder)

        return CreateReturnOrderResponse(
                returnOrderId = returnOrder.id,
                refundAmount = calculateRefundAmount(returnOrder.items),
        )
    }

    override fun pendingReturn(pendingRequest: PendingRequest): PendingResponse {
        var order = orderRepository.findByOrderIdAndEmailAddress(pendingRequest.orderId, pendingRequest.emailAddress)

        if (order != null) {
            return PendingResponse(token = "DEFAULT_TOKEN")
        } else {
            throw OrderNotFoundException()
        }
    }

    override fun getReturnOrder(id: Long): GetReturnOrderResponse {
        var returnOrder = returnOrderRepository.findByIdOrNull(id) ?: throw ReturnOrderNotFoundException()

        return GetReturnOrderResponse(
                refundAmount = calculateRefundAmount(items = returnOrder.items),
                status = returnOrder.status,
                items = returnOrder.items.map { GetItemsResponse(
                        itemId = it.itemId,
                        quantity = it.quantity,
                        qcStatus = it.qcStatus,
                        price = it.price,
                        itemName = it.itemName,
                        sku = it.sku
                ) }
        )
    }

    override fun updateQCStatus(returnOrderId: Long, itemId: Int, status: QcItemStatus): String {
        var item = itemRepository.updateItemStatus(status, itemId = itemId , returnOrderId = returnOrderId)

        if (item != null) return "OK" else throw ItemToReturnNotFoundException()
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

    private fun calculateRefundAmount(items: List<Item>): Double {
        var filteredItems = items.filter { it.qcStatus != QcItemStatus.REJECTED }
        return filteredItems.sumOf { it.price * it.quantity }
    }
}
