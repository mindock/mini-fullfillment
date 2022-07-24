package com.mindock.minifullfillment.controller

import com.mindock.minifullfillment.dto.stock.StockReceiveRequest
import com.mindock.minifullfillment.dto.stock.StockResponse
import com.mindock.minifullfillment.service.StockService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/stocks")
class StockController(
    private val stockService: StockService
) {

    @PostMapping
    fun receive(@Valid @RequestBody request: StockReceiveRequest): StockResponse =
        StockResponse.from(
            stockService.receive(request)
        )
}