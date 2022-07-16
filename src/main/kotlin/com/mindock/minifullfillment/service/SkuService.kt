package com.mindock.minifullfillment.service

import com.mindock.minifullfillment.domain.Sku
import com.mindock.minifullfillment.domain.SkuRepository
import com.mindock.minifullfillment.dto.sku.SkuCreateRequest
import com.mindock.minifullfillment.dto.sku.SkuUpdateRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SkuService(
    private val skuRepository: SkuRepository,
) {

    fun create(request: SkuCreateRequest): Sku {
        if (skuRepository.existsByCode(request.code)) {
            throw IllegalArgumentException("Sku 코드(${request.code})가 이미 존재합니다.")
        }

        val sku = Sku.of(
            name = request.name,
            code = request.code
        )
        return skuRepository.save(sku)
    }

    fun find(skuId: Long): Sku =
        skuRepository.findByIdOrNull(skuId)
            ?: throw IllegalArgumentException("Sku(ID: $skuId)를 찾을 수 없습니다.")

    @Transactional
    fun update(skuId: Long, request: SkuUpdateRequest): Sku {
        val sku = find(skuId)

        sku.update(request.status)
        return sku
    }
}