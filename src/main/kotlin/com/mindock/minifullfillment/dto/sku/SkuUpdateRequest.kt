package com.mindock.minifullfillment.dto.sku

import com.mindock.minifullfillment.domain.SkuStatus

data class SkuUpdateRequest(
    val status: SkuStatus
)
