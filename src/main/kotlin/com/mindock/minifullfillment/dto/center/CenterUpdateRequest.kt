package com.mindock.minifullfillment.dto.center

import com.mindock.minifullfillment.domain.CenterStatus

data class CenterUpdateRequest(
    val status: CenterStatus
)