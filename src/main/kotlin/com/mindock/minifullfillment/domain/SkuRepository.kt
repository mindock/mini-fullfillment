package com.mindock.minifullfillment.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SkuRepository : JpaRepository<Sku, Long> {

    fun existsByCode(code: String): Boolean
}