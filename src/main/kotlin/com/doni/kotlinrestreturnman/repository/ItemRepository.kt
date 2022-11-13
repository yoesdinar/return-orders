package com.doni.kotlinrestreturnman.repository

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.model.QcItemStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, String> {
    fun findByOrderIdAndQcStatusNotAndReturnOrderIdIsNull(orderId: String, qcStatus: QcItemStatus = QcItemStatus.REJECTED): List<Item>
}