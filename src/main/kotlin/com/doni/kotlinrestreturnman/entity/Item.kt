package com.doni.kotlinrestreturnman.entity

import com.doni.kotlinrestreturnman.model.QcItemStatus
import javax.persistence.*

@Entity
@Table(name = "items")
data class Item (

        @Column(name = "sku")
        val sku: String,

        @Column(name = "quantity")
        val quantity: Int,

        @Column(name = "price")
        val price: Int,

        @Column(name = "itemName")
        val itemName: String,

        @Column(name = "orderId")
        val orderId: String

) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val itemId: Int = 0

        @Column(name = "qcStatus")
        @Enumerated(EnumType.STRING)
        var qcStatus: QcItemStatus = QcItemStatus.PENDING

        @ManyToOne(fetch = FetchType.EAGER, optional = true)
        @JoinColumn(name = "returnOrderId")
        var returnOrder: ReturnOrder? = null
}
