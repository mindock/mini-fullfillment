package com.mindock.minifullfillment.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mindock.minifullfillment.domain.Center
import com.mindock.minifullfillment.domain.CenterStatus
import com.mindock.minifullfillment.domain.Sku
import com.mindock.minifullfillment.domain.SkuStatus
import com.mindock.minifullfillment.domain.Stock
import com.mindock.minifullfillment.dto.stock.StockReceiveRequest
import com.mindock.minifullfillment.service.StockService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(StockController::class)
internal class StockControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var stockService: StockService

    @Test
    fun `입고 수량이 0 이하인 경우, 400 응답`() {
        val request = StockReceiveRequest(
            centerId = 1L,
            skuId = 1L,
            quantity = -10
        )

        mockMvc
            .post("/api/v1/stocks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `입고 정상 입력값인 경우, 200 응답`() {
        val request = StockReceiveRequest(
            centerId = 1L,
            skuId = 1L,
            quantity = 10
        )

        val stock = Stock(
            center = Center.of(name = "판교점", status = CenterStatus.READY),
            sku = Sku.of(name = "포카칩", code = "potato", status = SkuStatus.SALE),
            quantity = 10,
        )
        every { stockService.receive(request) } returns stock

        mockMvc
            .post("/api/v1/stocks") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }
            .andExpect {
                status { isOk() }
            }
    }
}