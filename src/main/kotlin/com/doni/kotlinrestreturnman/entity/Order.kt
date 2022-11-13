package com.doni.kotlinrestreturnman.entity

import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
        @Id
        @Column(name = "orderId")
        val orderId: String,

        @Column(name = "emailAddress")
        val emailAddress: String,

        ) {
    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "returnOrderId")
    var returnOrder: ReturnOrder? = null

}