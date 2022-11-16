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

        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "order")
        @Column(insertable = false, updatable = false)
        var items : List<Item>
        ) {
}