package com.doni.kotlinrestreturnman.repository

import com.doni.kotlinrestreturnman.entity.ReturnOrder
import org.springframework.data.jpa.repository.JpaRepository

interface ReturnOrderRepository : JpaRepository<ReturnOrder, String> {

}