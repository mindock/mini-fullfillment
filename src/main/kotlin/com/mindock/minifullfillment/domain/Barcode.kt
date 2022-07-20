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
class Barcode(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barcode_id", nullable = false)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id", nullable = false)
    val sku: Sku,

    @Column(name = "code", length = 20, unique = true, nullable = false)
    val code: String
)