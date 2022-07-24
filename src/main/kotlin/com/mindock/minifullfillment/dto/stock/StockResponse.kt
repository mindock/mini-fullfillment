package com.mindock.minifullfillment.dto.stock

import com.mindock.minifullfillment.domain.Stock

data class StockResponse(
    val stockId: Long,
    val centerId: Long,
    val skuId: Long,
    val quantity: Int
) {
    companion object {
        fun from(stock: Stock): StockResponse =
            StockResponse(
                stockId = stock.id,
                centerId = stock.center.id,
                skuId = stock.sku.id,
                quantity = stock.quantity
            )
    }
}
