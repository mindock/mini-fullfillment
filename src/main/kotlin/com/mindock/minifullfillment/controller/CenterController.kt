package com.mindock.minifullfillment.controller

import com.mindock.minifullfillment.dto.center.CenterCreateRequest
import com.mindock.minifullfillment.dto.center.CenterResponse
import com.mindock.minifullfillment.dto.center.CenterUpdateRequest
import com.mindock.minifullfillment.service.CenterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/centers")
class CenterController(
    private val centerService: CenterService,
) {

    @PostMapping
    fun create(@RequestBody request: CenterCreateRequest): CenterResponse {
        return CenterResponse.from(
            centerService.create(request)
        )
    }

    @GetMapping("/{centerId}")
    fun getCenter(@PathVariable centerId: Long): CenterResponse {
        return CenterResponse.from(
            centerService.find(centerId)
        )
    }

    @PutMapping("/{centerId}")
    fun update(
        @PathVariable centerId: Long,
        @RequestBody request: CenterUpdateRequest
    ): CenterResponse {
        return CenterResponse.from(
            centerService.update(centerId, request)
        )
    }
}