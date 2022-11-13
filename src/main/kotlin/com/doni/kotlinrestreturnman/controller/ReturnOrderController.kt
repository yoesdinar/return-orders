package com.doni.kotlinrestreturnman.controller

import com.doni.kotlinrestreturnman.model.*
import com.doni.kotlinrestreturnman.service.ReturnOrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReturnOrderController(val returnOrderService: ReturnOrderService) {

    @PostMapping(
            value = ["/pending/returns"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun createReturnOrder(@RequestBody body: PendingRequest): WebResponse<PendingResponse> {
        val returnPendingResponse = returnOrderService.pendingReturn(body)
        return WebResponse(
                code = 200,
                status = "OK",
                data = returnPendingResponse
        )
    }
    @PostMapping(
            value = ["/returns"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun createReturnOrder(@RequestBody body: CreateReturnOrderRequest): WebResponse<CreateReturnOrderResponse> {
        val returnOrderResponse = returnOrderService.createReturnOrder(body)
        return WebResponse(
                code = 200,
                status = "OK",
                data = returnOrderResponse
        )
    }
}
