package com.doni.kotlinrestreturnman.entity

import com.doni.kotlinrestreturnman.model.QcItemStatus
import javax.persistence.*

@Entity
@Table(name = "items")
data class Item (

        @Id
        @Column(name = "sku")
        val sku: String,

        @Column(name = "quantity")
        val quantity: Int,

        @Column(name = "price")
        val price: Int,

        @Column(name = "itemName")
        val itemName: String,

        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "orderId")
        val order: Order

) {
        @Column(name = "qcStatus")
        var qcStatus: QcItemStatus? = null

        @ManyToOne(fetch = FetchType.EAGER, optional = true)
        @JoinColumn(name = "returnOrderId")
        var returnOrder: ReturnOrder? = null
}
