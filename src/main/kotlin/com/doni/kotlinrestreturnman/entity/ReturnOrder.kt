package com.doni.kotlinrestreturnman.entity

import com.doni.kotlinrestreturnman.model.ReturnOrderStatus
import javax.persistence.*

@Entity
@Table(name = "returnOrders")
data class ReturnOrder(

        @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "returnOrder")
        val order: Order,

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "returnOrder")
        val items : List<Item>

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(name = "status")
    val status: ReturnOrderStatus = ReturnOrderStatus.AWAITING_APPROVAL

}