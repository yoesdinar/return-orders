package com.doni.kotlinrestreturnman.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class Order (

        @Id
        val id: String,

        @Column(name = "emailAddress")
        val emailAddress: String,
)
