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

    @Enumerated(EnumType.STRING)
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