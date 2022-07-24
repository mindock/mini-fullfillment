package com.mindock.minifullfillment.service

import com.mindock.minifullfillment.domain.Center
import com.mindock.minifullfillment.domain.CenterRepository
import com.mindock.minifullfillment.domain.Sku
import com.mindock.minifullfillment.domain.SkuRepository
import com.mindock.minifullfillment.domain.Stock
import com.mindock.minifullfillment.domain.StockRepository
import com.mindock.minifullfillment.dto.stock.StockReceiveRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val centerRepository: CenterRepository,
    private val skuRepository: SkuRepository,
    private val stockRepository: StockRepository
) {

    @Transactional
    fun receive(request: StockReceiveRequest): Stock {
        val center = centerRepository.findByIdOrNull(request.centerId)
            ?.also { check(it.canReceive) { "Center 입고할 상태가 아닙니다. (status: ${it.status})" } }
            ?: throw IllegalArgumentException("Center(ID: ${request.centerId})를 찾을 수 없습니다.")
        val sku = skuRepository.findByIdOrNull(request.skuId)
            ?.also { check(it.canReceive) { "Sku 입고할 상태가 아닙니다. (status: ${it.status})" } }
            ?: throw IllegalArgumentException("Sku(ID: ${request.skuId})를 찾을 수 없습니다.")

        val registeredStock = stockRepository.findByCenterAndSku(center, sku)

        return if (registeredStock == null) {
            registerStock(center, sku, request.quantity)
        } else {
            updateStock(registeredStock, request.quantity)
        }
    }

    private fun registerStock(center: Center, sku: Sku, quantity: Int): Stock {
        val stock = stockRepository.save(
            Stock.of(center, sku, quantity)
        )
        center.addStock(stock)

        return stock
    }

    private fun updateStock(stock: Stock, quantity: Int): Stock {
        stock.addQuantity(quantity)

        return stock
    }
}