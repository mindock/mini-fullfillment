package com.mindock.minifullfillment.domain

enum class SkuStatus(val description: String) {
    READY("런칭 예정"),
    SALE("판매 중"),
    STOP("판매 중지");
}