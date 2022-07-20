package com.mindock.minifullfillment.dto.center

import com.mindock.minifullfillment.domain.Center
import com.mindock.minifullfillment.domain.CenterStatus

data class CenterResponse(
    val id: Long,
    val name: String,
    val status: CenterStatus,
) {

    companion object {
        fun from(center: Center): CenterResponse =
            CenterResponse(
                id = center.id,
                name = center.name,
                status = center.status
            )
    }
}