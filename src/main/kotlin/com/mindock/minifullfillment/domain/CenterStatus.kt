package com.mindock.minifullfillment.domain

enum class CenterStatus(val description: String, val canReceive: Boolean) {
    READY("오픈 준비 중", true),
    OPERATING("운영 중", true),
    CLOSED("폐점", false);

}
