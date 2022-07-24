package com.mindock.minifullfillment.service

import com.mindock.minifullfillment.domain.Center
import com.mindock.minifullfillment.domain.CenterRepository
import com.mindock.minifullfillment.domain.CenterStatus
import com.mindock.minifullfillment.domain.Sku
import com.mindock.minifullfillment.domain.SkuRepository
import com.mindock.minifullfillment.domain.SkuStatus
import com.mindock.minifullfillment.domain.Stock
import com.mindock.minifullfillment.domain.StockRepository
import com.mindock.minifullfillment.dto.stock.StockReceiveRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

internal class StockServiceTest {

    private val centerRepository: CenterRepository = mockk()
    private val skuRepository: SkuRepository = mockk()
    private val stockRepository: StockRepository = mockk {
        every { save(any()) } returnsArgument 0
    }

    private val stockService: StockService = StockService(
        centerRepository,
        skuRepository,
        stockRepository
    )

    @Nested
    inner class ReceiveTest {

        val centerId = 1L
        val skuId = 2L
        val quantity = 10

        @Test
        fun `center 존재하지 않는 경우, exception 발생`() {
            every { centerRepository.findByIdOrNull(centerId) } returns null

            assertThrows<IllegalArgumentException> {
                stockService.receive(
                    StockReceiveRequest(centerId, skuId, quantity)
                )
            }
        }

        @Test
        fun `center 입고 불가한 상태인 경우, exception 발생`() {
            val center = Center.of(
                name = "판교점",
                status = CenterStatus.CLOSED
            )
            every { centerRepository.findByIdOrNull(centerId) } returns center

            assertThrows<IllegalStateException> {
                stockService.receive(
                    StockReceiveRequest(centerId, skuId, quantity)
                )
            }
        }

        @Test
        fun `sku 존재하지 않는 경우, exception 발생`() {
            val center = Center.of(
                name = "판교점",
                status = CenterStatus.READY
            )
            every { centerRepository.findByIdOrNull(centerId) } returns center
            every { skuRepository.findByIdOrNull(skuId) } returns null

            assertThrows<IllegalArgumentException> {
                stockService.receive(
                    StockReceiveRequest(centerId, skuId, quantity)
                )
            }
        }

        @Test
        fun `sku 입고 불가한 상태인 경우, exception 발생`() {
            val center = Center.of(
                name = "판교점",
                status = CenterStatus.READY
            )
            val sku = Sku.of(
                name = "빼빼로",
                code = "abc",
                status = SkuStatus.STOP
            )
            every { centerRepository.findByIdOrNull(centerId) } returns center
            every { skuRepository.findByIdOrNull(skuId) } returns sku

            assertThrows<IllegalStateException> {
                stockService.receive(
                    StockReceiveRequest(centerId, skuId, quantity)
                )
            }
        }

        @Test
        fun `등록된 재고 정보가 없을 경우, stock 생성`() {
            val receivedQuantity = 10

            val center = Center.of(
                name = "판교점",
                status = CenterStatus.READY
            )
            val sku = Sku.of(
                name = "빼빼로",
                code = "abc",
                status = SkuStatus.READY
            )
            every { centerRepository.findByIdOrNull(centerId) } returns center
            every { skuRepository.findByIdOrNull(skuId) } returns sku
            every { stockRepository.findByCenterAndSku(center, sku) } returns null

            val result = stockService.receive(
                StockReceiveRequest(centerId, skuId, quantity)
            )

            assertEquals(center, result.center)
            assertEquals(sku, result.sku)
            assertEquals(receivedQuantity, result.quantity)
        }

        @Test
        fun `등록된 재고 정보가 있을 경우, stock 수량 추가`() {
            val previousQuantity = 3
            val receivedQuantity = 10

            val center = Center.of(
                name = "판교점",
                status = CenterStatus.OPERATING
            )
            val sku = Sku.of(
                name = "빼빼로",
                code = "abc",
                status = SkuStatus.SALE
            )
            val stock = Stock.of(
                center = center,
                sku = sku,
                quantity = previousQuantity
            )
            every { centerRepository.findByIdOrNull(centerId) } returns center
            every { skuRepository.findByIdOrNull(skuId) } returns sku
            every { stockRepository.findByCenterAndSku(center, sku) } returns stock

            val result = stockService.receive(
                StockReceiveRequest(centerId, skuId, quantity)
            )

            assertEquals(center, result.center)
            assertEquals(sku, result.sku)
            assertEquals(previousQuantity + receivedQuantity, result.quantity)
        }
    }
}