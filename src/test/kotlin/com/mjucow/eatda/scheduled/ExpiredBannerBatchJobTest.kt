package com.mjucow.eatda.scheduled

import com.mjucow.eatda.domain.AbstractDataTest
import com.mjucow.eatda.domain.banner.entity.objectmother.BannerMother
import com.mjucow.eatda.jooq.tables.ExpiredBanner.EXPIRED_BANNER
import com.mjucow.eatda.jooq.tables.records.BannerRecord
import com.mjucow.eatda.jooq.tables.records.ExpiredBannerRecord
import com.mjucow.eatda.persistence.banner.BannerRepository
import org.assertj.core.api.Assertions
import org.jooq.DSLContext
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import java.time.LocalDateTime
import java.util.stream.IntStream

@Import(value = [ExpiredBannerBatchJob::class])
class ExpiredBannerBatchJobTest : AbstractDataTest() {
    @Autowired
    lateinit var db: DSLContext

    @Autowired
    lateinit var bannerRepository: BannerRepository

    @Autowired
    lateinit var expiredBannerBatchJob: ExpiredBannerBatchJob

    @DisplayName("배치할 거 없으면 따로 동작 안하기")
    @Test
    fun test1() {
        // given

        // when
        expiredBannerBatchJob.scheduleTaskUsingCronExpression()

        // then
        val createdExpiredBanners = findAllExpiredBanner()
        Assertions.assertThat(createdExpiredBanners).isEmpty()
    }

    @DisplayName("배치할 거 있으면 expiredBanner로 데이터 복사하기")
    @Test
    fun test2() {
        // given
        val now = LocalDateTime.now(ZONE_ID)
        val expiredAt = now.minusDays(1)
        val count = 3
        db.batchInsert(
            IntStream
                .range(0, count)
                .mapToObj {
                    BannerRecord().apply {
                        this.imageAddress = BannerMother.IMAGE_ADDRESS
                        this.link = BannerMother.LINK
                        this.expiredAt = expiredAt
                        this.createdAt = now
                        this.updatedAt = now
                    }
                }.toList()
        ).execute()

        // when
        expiredBannerBatchJob.scheduleTaskUsingCronExpression()

        // then
        val createdExpiredBanners = findAllExpiredBanner()
        Assertions.assertThat(createdExpiredBanners).hasSize(count)
        Assertions.assertThat(bannerRepository.findAll()).isEmpty()

    }

    private fun findAllExpiredBanner(): List<ExpiredBannerRecord> {
        return db.selectQuery(EXPIRED_BANNER).toList()
    }

    companion object {
        val ZONE_ID = ExpiredBannerBatchJob.ZONE_ID
    }
}
