package com.mindock.minifullfillment.domain

import javax.persistence.*

@Entity
class Center(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    val id: Long = 0L,

    val name: String,

    var status: CenterStatus,

    @OneToMany(mappedBy = "center")
    var stocks: MutableList<Stock> = mutableListOf(),
)