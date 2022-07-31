package com.mindock.minifullfillment.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class StockTest {

    private val center = Center.of("홍대점", CenterStatus.OPERATING)
    private val sku = Sku.of("바나나우유", "banana_milk", SkuStatus.SALE)

    @Test
    fun `재고 생성 시, 수량이 0 미만인 경우 예외 발생`() {
        assertThrows<IllegalStateException> {
            Stock.of(center, sku, -10)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 1, 10, 100])
    fun `재고 생성 시, 수량이 0 이상이어야 합니다`(quantity: Int) {
        assertDoesNotThrow {
            Stock.of(center, sku, quantity)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [-10, -1, 0])
    fun `재고 수량 증가 시, 증가시킬 수량이 0 이하일 경우 예외 발생`(quantity: Int) {
        val stock = Stock.of(center, sku, 0)

        assertThrows<IllegalStateException> {
            stock.addQuantity(quantity)
        }
    }

    @Test
    fun `재고 수량 증가 시, 증가시킬 수량이 0 초과이어야 합니다`() {
        val stock = Stock.of(center, sku, 0)

        assertDoesNotThrow {
            stock.addQuantity(5)
        }
        assertEquals(5, stock.quantity)
    }
}