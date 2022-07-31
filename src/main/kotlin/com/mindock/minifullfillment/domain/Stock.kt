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
    @Column(name = "stock_id", nullable = false)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    val center: Center,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id", nullable = false)
    val sku: Sku,

    @Column(name = "quantity", nullable = false)
    var quantity: Int
) {

    init {
        check(quantity >= 0) { "재고 수량은 0 이상이어야 합니다." }
    }

    fun addQuantity(quantity: Int) {
        check(quantity > 0) { "입고 수량은 0 초과이어야 합니다." }
        this.quantity += quantity
    }

    companion object {
        fun of(center: Center, sku: Sku, quantity: Int): Stock =
            Stock(
                center = center,
                sku = sku,
                quantity = quantity
            )
    }
}