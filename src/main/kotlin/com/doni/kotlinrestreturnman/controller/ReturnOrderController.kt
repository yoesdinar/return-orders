package com.doni.kotlinrestreturnman.controller

import com.doni.kotlinrestreturnman.model.*
import com.doni.kotlinrestreturnman.service.ReturnOrderService
import org.springframework.web.bind.annotation.*

@RestController
class ReturnOrderController(val returnOrderService: ReturnOrderService) {

    @PostMapping(
            value = ["/pending/returns"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun pendingReturn(@RequestBody body: PendingRequest): WebResponse<PendingResponse> {
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

    @GetMapping(
            value = ["/returns/{id}"],
            produces = ["application/json"]
    )
    fun getReturnOrder(@PathVariable("id") id: Long): WebResponse<GetReturnOrderResponse> {
        val getReturnOrderResponse = returnOrderService.getReturnOrder(id)
        return WebResponse(
                code = 200,
                status = "OK",
                data = getReturnOrderResponse
        )
    }
}
