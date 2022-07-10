package com.mindock.minifullfillment.controller

import com.mindock.minifullfillment.dto.center.CenterCreateRequest
import com.mindock.minifullfillment.dto.center.CenterResponse
import com.mindock.minifullfillment.dto.center.CenterUpdateRequest
import com.mindock.minifullfillment.dto.sku.SkuCreateRequest
import com.mindock.minifullfillment.dto.sku.SkuResponse
import com.mindock.minifullfillment.dto.sku.SkuUpdateRequest
import com.mindock.minifullfillment.service.SkuService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/skus")
class SkuController(
    private val skuService: SkuService
) {

    @PostMapping
    fun create(
        @RequestBody request: SkuCreateRequest
    ): SkuResponse {
        return SkuResponse.from(
            skuService.create(request)
        )
    }

    @GetMapping("/{skuId}")
    fun getCenter(
        @PathVariable skuId: Long,
    ): SkuResponse {
        return SkuResponse.from(
            skuService.find(skuId)
        )
    }

    @PutMapping("/{skuId}")
    fun update(
        @PathVariable skuId: Long,
        @RequestBody request: SkuUpdateRequest
    ): SkuResponse {
        return SkuResponse.from(
            skuService.update(skuId, request)
        )
    }
}