package com.mindock.minifullfillment.service

import com.mindock.minifullfillment.domain.SkuRepository
import com.mindock.minifullfillment.dto.sku.SkuCreateRequest
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class SkuServiceTest {

    private val skuRepository: SkuRepository = mockk {
        every { save(any()) } answers { firstArg() }
    }

    private val skuService: SkuService = SkuService(skuRepository)

    @Test
    fun `이미 존재하는 코드로 sku 생성 시 exception 발생`() {
        val code = "1234ABCD"
        every { skuRepository.existsByCode(code) } returns true

        assertThrows<IllegalArgumentException> { skuService.create(SkuCreateRequest(name = "새우깡", code = code)) }
    }

    @Test
    fun `존재하지 않은 코드로 sku 생성 시 exception 발생하지 않음`() {
        val code = "1234ABCD"
        every { skuRepository.existsByCode(code) } returns false

        assertDoesNotThrow { skuService.create(SkuCreateRequest(name = "새우깡", code = code)) }
    }
}