package com.doni.kotlinrestreturnman.service.impl

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.entity.Order
import com.doni.kotlinrestreturnman.entity.ReturnOrder
import com.doni.kotlinrestreturnman.error.ItemToReturnNotFoundException
import com.doni.kotlinrestreturnman.error.OrderNotFoundException
import com.doni.kotlinrestreturnman.error.UnauthorizedException
import com.doni.kotlinrestreturnman.model.CreateReturnOrderRequest
import com.doni.kotlinrestreturnman.model.PendingRequest
import com.doni.kotlinrestreturnman.model.QcItemStatus
import com.doni.kotlinrestreturnman.model.ReturnOrderStatus
import com.doni.kotlinrestreturnman.repository.ItemRepository
import com.doni.kotlinrestreturnman.repository.OrderRepository
import com.doni.kotlinrestreturnman.repository.ReturnOrderRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.lang.Double

internal class ReturnOrderServiceImplTest {

    private val itemRepository: ItemRepository = mockk(relaxed = true)
    private val orderRepository: OrderRepository = mockk(relaxed = true)
    private val returnOrderRepository: ReturnOrderRepository = mockk(relaxed = true)
    private val returnOrderService = ReturnOrderServiceImpl(returnOrderRepository, orderRepository, itemRepository)

    @Test
    fun `when create request is complete should call repository save`() {
        
        var createReturnOrderRequest = CreateReturnOrderRequest (
            orderId = "orderId",
            emailAddress = "email",
            token = "DEFAULT_TOKEN",
            items = listOf(0)
        )

        var items = listOf<Item>(
               Item("sku", 2, 10.0, "nama item"),
               Item("sku", 2, 10.0, "nama item")
        )
        var order = Order(
                orderId = "orderId",
                emailAddress = "email",
                items= items
        )

        var returnOrder = ReturnOrder(items)

        every { orderRepository.findByOrderIdAndEmailAddress(any(), any())  } returns (order)
        every { itemRepository.findByOrderOrderIdAndReturnOrderIdIsNull(any())  } returns (items)
        every { returnOrderRepository.save(any())  } returns (returnOrder)

        returnOrderService.createReturnOrder(createReturnOrderRequest)
        // then
        verify(exactly = 1) { itemRepository.findByOrderOrderIdAndReturnOrderIdIsNull(any()) }
        verify(exactly = 1) { returnOrderRepository.save(any()) }
    }

    @Test
    fun `when create request have wrong token should return Unauthorized Exception`() {
        
        var createReturnOrderRequest = CreateReturnOrderRequest (
                orderId = "orderId",
                emailAddress = "email",
                items = listOf(0),
                token = "wrong"
        )
        var items = listOf<Item>(
                Item("sku", 2, 10.0, "nama item"),
                Item("sku", 2, 10.0, "nama item")
        )
        var order = Order(
                orderId = "orderId",
                emailAddress = "email",
                items = items
        )

        var returnOrder = ReturnOrder(items)

        every { orderRepository.findByOrderIdAndEmailAddress(any(), any())  } returns (order)
        every { itemRepository.findByOrderOrderIdAndReturnOrderIdIsNull(any())  } returns (items)
        every { returnOrderRepository.save(any())  } returns (returnOrder)


        assertThrows(
                UnauthorizedException::class.java,
                { returnOrderService.createReturnOrder(createReturnOrderRequest) },
                "Please put your Token or your Token is Wrong"
        )
    }

    @Test
    fun `when items entity not valid not found should return ItemToReturnNotFound Exception`() {
        var createReturnOrderRequest = CreateReturnOrderRequest (
                orderId = "orderId",
                emailAddress = "email",
                items = listOf(0),
                token = "DEFAULT_TOKEN"
        )

        var items = listOf<Item>(
        )

        var order = Order(
                orderId = "orderId",
                emailAddress = "email",
                items = items
        )

        every { orderRepository.findByOrderIdAndEmailAddress(any(), any())  } returns (order)
        every { itemRepository.findByOrderOrderIdAndReturnOrderIdIsNull(any())  } returns (items)

        assertThrows(
                ItemToReturnNotFoundException::class.java,
                { returnOrderService.createReturnOrder(createReturnOrderRequest) },
                "Item To Return Not Found"
        )
    }

    @Test
    fun `when order and email valid should return token`() {
        var pendingRequest = PendingRequest(
                emailAddress = "email",
                orderId = "orderId"
        )
        var order = Order(
                orderId = "orderId",
                emailAddress = "email",
                items = listOf()
        )

        every { orderRepository.findByOrderIdAndEmailAddress(any(), any())  } returns (order)
        var pendingResponse = returnOrderService.pendingReturn(pendingRequest);
        assertEquals(pendingResponse.token, "DEFAULT_TOKEN")

    }

    @Test
    fun `when order and email not valid should throw OrderNotFoundException `() {
        var pendingRequest = PendingRequest(
                emailAddress = "email",
                orderId = "orderId"
        )
        var order = null

        every { orderRepository.findByOrderIdAndEmailAddress(any(), any())  } returns (order)

        assertThrows(
                OrderNotFoundException::class.java,
                { returnOrderService.pendingReturn(pendingRequest) },
                "Order Not Found"
        )

    }

    @Test
    fun `when returnOrderId exist should return ReturnOrderResponse with valid refund`() {
        var id: Long = 1

        var items = listOf<Item>(
                Item("sku", 2, 10.0, "nama item"),
                Item("sku", 2, 10.0, "nama item")
        )

        var returnOrder = ReturnOrder(items)

        every { returnOrderRepository.findByIdOrNull(any())  } returns (returnOrder)

        var returnOrderResponse = returnOrderService.getReturnOrder(id)
        assertEquals(returnOrderResponse.refundAmount, Double(40.0))
        assertEquals(returnOrderResponse.status, ReturnOrderStatus.AWAITING_APPROVAL)
        assertEquals(returnOrderResponse.items.count(), 2)
    }

    @Test
    fun `when returnOrderId exist should return ReturnOrderResponse with valid refund with rejected QC status`() {
        var id: Long = 1


        var item1 =  Item("sku", 2, 15.0, "nama item")
        item1.qcStatus = QcItemStatus.REJECTED
        var item2 =  Item("sku", 1, 14.0, "nama item")
        item2.qcStatus = QcItemStatus.ACCEPTED
        var item3 =  Item("sku", 1, 10.0, "nama item")

        var items = listOf<Item>(
               item1, item2, item3
        )

        var order = Order(
                orderId = "orderId",
                emailAddress = "email",
                items = items
        )

        var returnOrder = ReturnOrder(items)

        every { returnOrderRepository.findByIdOrNull(any())  } returns (returnOrder)

        var returnOrderResponse = returnOrderService.getReturnOrder(id)
        assertEquals(returnOrderResponse.refundAmount, Double(24.0)) // same with the accepted and pending
        assertEquals(returnOrderResponse.status, ReturnOrderStatus.AWAITING_APPROVAL)
        assertEquals(returnOrderResponse.items.count(), 3)
    }

    @Test
    fun `when updateqc status valid input should return OK with valid data`() {
        var id: Long = 1

        every { itemRepository.updateItemStatus(any(), any(), any())  } returns (Unit)

        var updateQCResponse = returnOrderService.updateQCStatus(id, 1, QcItemStatus.ACCEPTED)
        assertEquals(updateQCResponse, "OK")

    }
}