package com.mindock.minifullfillment.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Stock(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    val center: Center,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    val sku: Sku,

    var quantity: Int
)