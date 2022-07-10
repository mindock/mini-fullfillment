package com.mindock.minifullfillment.domain

import javax.persistence.*

@Entity
class Barcode(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barcode_id")
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    val sku: Sku,

    val code: String
)