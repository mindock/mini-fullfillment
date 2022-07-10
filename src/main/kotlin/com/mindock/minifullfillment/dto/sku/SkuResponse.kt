package com.mindock.minifullfillment.dto.sku

import com.mindock.minifullfillment.domain.Sku
import com.mindock.minifullfillment.domain.SkuStatus

data class SkuResponse(
    val id: Long,
    val name: String,
    val code: String,
    val status: SkuStatus
) {

    companion object {
        fun from(sku: Sku): SkuResponse =
            SkuResponse(
                id = sku.id,
                name = sku.name,
                code = sku.code,
                status = sku.status
            )
    }
}