package com.doni.kotlinrestreturnman.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "items")
data class Item (

        @Id
        val Id: String,

        @Column(name = "orderId")
        val orderId: String,

        @Column(name = "emailAddress")
        val emailAddress: String,

        @Column(name = "sku")
        val sku: String,

        @Column(name = "quantity")
        val quantity: Int,

        @Column(name = "price")
        val price: Int,

        @Column(name = "itemName")
        val itemName: String
)
