package com.doni.kotlinrestreturnman.controller

import com.doni.kotlinrestreturnman.model.CreateReturnOrderRequest
import com.doni.kotlinrestreturnman.model.ReturnOrderResponse
import com.doni.kotlinrestreturnman.model.WebResponse
import com.doni.kotlinrestreturnman.service.ReturnOrderService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReturnOrderController(val returnOrderService: ReturnOrderService) {

    @PostMapping(
            value = ["/returns"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun createReturnOrder(@RequestBody body: CreateReturnOrderRequest): WebResponse<ReturnOrderResponse> {
        val returnOrderResponse = returnOrderService.createReturnOrder(body)
        return WebResponse(
                code = 200,
                status = "OK",
                data = returnOrderResponse
        )
    }
}
