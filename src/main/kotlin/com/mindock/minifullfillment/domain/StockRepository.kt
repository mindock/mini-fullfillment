package com.mindock.minifullfillment.domain

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long> {

    fun findByCenterAndSku(center: Center, sku: Sku): Stock?
}