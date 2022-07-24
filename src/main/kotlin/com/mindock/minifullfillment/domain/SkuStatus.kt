package com.mindock.minifullfillment.domain

enum class SkuStatus(val description: String, val canReceive: Boolean) {
    READY("런칭 예정", true),
    SALE("판매 중", true),
    STOP("판매 중지", false);
}