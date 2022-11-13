package com.doni.kotlinrestreturnman.entity

import com.doni.kotlinrestreturnman.model.ReturnOrderStatus
import javax.persistence.*

@Entity
@Table(name = "returnOrders")
data class ReturnOrder(

        @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER, mappedBy = "returnOrder")
        var order: Order,

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "returnOrder")
        var items : List<Item>

) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: ReturnOrderStatus = ReturnOrderStatus.AWAITING_APPROVAL

}