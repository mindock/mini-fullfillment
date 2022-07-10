package com.mindock.minifullfillment.service

import com.mindock.minifullfillment.domain.Center
import com.mindock.minifullfillment.domain.CenterRepository
import com.mindock.minifullfillment.domain.CenterStatus
import com.mindock.minifullfillment.dto.center.CenterCreateRequest
import com.mindock.minifullfillment.dto.center.CenterUpdateRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CenterService(
    private val centerRepository: CenterRepository
) {

    fun create(request: CenterCreateRequest): Center {
        val center = Center.of(name = request.name, status = CenterStatus.READY)
        return centerRepository.save(center)
    }

    fun find(centerId: Long): Center =
        centerRepository.findByIdOrNull(centerId)
            ?: throw IllegalArgumentException("Center(ID: $centerId)를 찾을 수 없습니다.")

    @Transactional
    fun update(centerId: Long, request: CenterUpdateRequest): Center {
        val center = find(centerId)

        center.update(request.status)
        return center
    }
}