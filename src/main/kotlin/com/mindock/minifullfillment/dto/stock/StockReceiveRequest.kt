package com.mindock.minifullfillment.dto.stock

import javax.validation.constraints.Positive

data class StockReceiveRequest(
    val centerId: Long,
    val skuId: Long,
    @field:Positive(message = "입고 수량은 양수이어야 합니다.")
    val quantity: Int,
)