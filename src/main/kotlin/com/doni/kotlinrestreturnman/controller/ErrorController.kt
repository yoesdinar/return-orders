package com.doni.kotlinrestreturnman.controller


import com.doni.kotlinrestreturnman.error.ItemToReturnNotFoundException
import com.doni.kotlinrestreturnman.error.OrderNotFoundException
import com.doni.kotlinrestreturnman.error.ReturnOrderNotFoundException
import com.doni.kotlinrestreturnman.error.UnauthorizedException
import com.doni.kotlinrestreturnman.model.WebResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [ItemToReturnNotFoundException::class])
    fun itemNotFoundHandler(itemToReturnNotFoundException: ItemToReturnNotFoundException): WebResponse<String> {
        return WebResponse(
                code = 400,
                status = "Not Found",
                data = "Item not found or already in refund process"
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [OrderNotFoundException::class])
    fun orderNotFoundHandler(orderNotFoundException: OrderNotFoundException): WebResponse<String> {
        return WebResponse(
                code = 400,
                status = "Not Found",
                data = "Order not found"
        )
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [ReturnOrderNotFoundException::class])
    fun returnOrderNotFound(returnOrderNotFoundException: ReturnOrderNotFoundException): WebResponse<String> {
        return WebResponse(
                code = 404,
                status = "NOT FOUND",
                data = "ReturnOrder you are looking is not Found"
        )
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedException::class])
    fun unauthorized(unauthorizedException: UnauthorizedException): WebResponse<String> {
        return WebResponse(
                code = 401,
                status = "UNAUTHORIZED",
                data = "Please put your Token or your Token is Wrong"
        )
    }

}