package com.mindock.minifullfillment.service

import com.mindock.minifullfillment.domain.StockRepository
import com.mindock.minifullfillment.dto.center.CenterCreateRequest
import com.mindock.minifullfillment.dto.sku.SkuCreateRequest
import com.mindock.minifullfillment.dto.stock.StockReceiveRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class StockServiceIntegrationTest {
    @Autowired
    private lateinit var skuService: SkuService

    @Autowired
    private lateinit var centerService: CenterService

    @Autowired
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var stockRepository: StockRepository

    @Autowired
    private lateinit var testExecutor: ThreadPoolTaskExecutor

    /**
     * 총 100개의 thread에서 동시에 receive를 했을 때 아래와 같이 두개의 이슈가 발생하고 있습니다.
     * 이를 고민해보고 해결해보면 좋을 것 같아요!
     *
     * 1. Stock에 대한 insert가 N번 수행되고 있어요 -> 이로인해 stockRepository.findByCenterAndSku의 결과값이 1개가 아닌 현상이 발생하고 있어요
     *
     * 2. corePoolSize만큼 receive를 했는데, quantity가 의도한 개수 만큼 증가되지 않은 것 같아요 -> 100개를 의도했지만 100개가 아닌 것 같아요
     */
    @Test
    internal fun 동시성_테스트() {
        val sku = skuService.create(SkuCreateRequest(name = "van", code = "van"))
        val center = centerService.create(CenterCreateRequest("van"))

        val request = StockReceiveRequest(
            centerId = center.id,
            skuId = sku.id,
            quantity = 1
        )
        repeat(testExecutor.corePoolSize) {
            testExecutor.execute {
                stockService.receive(request)
            }
        }

        val stock = stockRepository.findByCenterAndSku(center, sku)!!
        assertThat(stock.quantity).isEqualTo(100)
    }
}

@Configuration
class TestExecutorConfiguration {

    @Bean
    fun testExecutor(): ThreadPoolTaskExecutor {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor().apply {
            corePoolSize = 100
            maxPoolSize = 100
            queueCapacity = Integer.MAX_VALUE
            keepAliveSeconds = 60
            setBeanName("taskExecutor")
        }
        return threadPoolTaskExecutor
    }
}
