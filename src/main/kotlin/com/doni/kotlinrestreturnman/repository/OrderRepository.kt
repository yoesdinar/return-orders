package com.doni.kotlinrestreturnman.repository

import com.doni.kotlinrestreturnman.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, String> {
    fun findByOrderIdAndEmailAddress(orderId: String, emailAddress: String): Order?
}