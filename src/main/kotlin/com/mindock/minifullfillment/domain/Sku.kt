package com.mindock.minifullfillment.domain

import javax.persistence.*

@Entity
class Sku(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sku_id")
    val id: Long = 0L,

    val name: String,

    val code: String,

    var status: SkuStatus,

    @OneToMany(mappedBy = "sku")
    var barcodes: MutableList<Barcode> = mutableListOf(),
)