package com.mindock.minifullfillment.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany


@Entity
class Center(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id", nullable = false)
    val id: Long = 0L,

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: CenterStatus,

    @OneToMany(mappedBy = "center")
    var stocks: MutableList<Stock> = mutableListOf(),
) {

    val canReceive: Boolean
        get() = status.canReceive

    fun update(status: CenterStatus) {
        this.status = status
    }

    fun addStock(stock: Stock) {
        this.stocks.add(stock)
    }

    companion object {
        fun of(name: String, status: CenterStatus): Center =
            Center(
                name = name,
                status = status
            )
    }
}