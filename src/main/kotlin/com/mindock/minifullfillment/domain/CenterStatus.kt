package com.mindock.minifullfillment.domain

enum class CenterStatus(val description: String) {
    READY("오픈 준비 중"),
    OPERATING("운영 중"),
    CLOSED("폐점");
}
