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
class Sku(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sku_id", nullable = false)
    val id: Long = 0L,

    @Column(name = "name", length = 20, nullable = false)
    val name: String,

    @Column(name = "code", unique = true, nullable = false)
    val code: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: SkuStatus,

    @OneToMany(mappedBy = "sku")
    var barcodes: MutableList<Barcode> = mutableListOf(),
) {

    fun update(status: SkuStatus) {
        this.status = status
    }

    companion object {
        fun of(name: String, code: String): Sku =
            Sku(
                name = name,
                code = code,
                status = SkuStatus.READY
            )
    }
}