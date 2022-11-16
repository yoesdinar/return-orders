package com.doni.kotlinrestreturnman.repository

import com.doni.kotlinrestreturnman.entity.Item
import com.doni.kotlinrestreturnman.model.QcItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional


interface ItemRepository : JpaRepository<Item, String> {
    fun findByOrderOrderIdAndReturnOrderIdIsNull(orderId: String): List<Item>

    @Modifying
    @Transactional
    @Query("update Item u set u.qcStatus = :status where u.returnOrder.id = :returnOrderId and u.itemId = :itemId")
    fun updateItemStatus(@Param("status") status: QcItemStatus, @Param("returnOrderId") returnOrderId: Long, @Param("itemId") itemId: Int)
}
