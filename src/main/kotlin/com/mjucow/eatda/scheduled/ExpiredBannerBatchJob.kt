package com.mjucow.eatda.scheduled

import com.mjucow.eatda.jooq.tables.Banner.BANNER
import com.mjucow.eatda.jooq.tables.records.ExpiredBannerRecord
import org.jooq.DSLContext
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


@Component
class ExpiredBannerBatchJob(
    private val db: DSLContext,
) {
    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Seoul") // 매일 오후 10시 동작
    fun scheduleTaskUsingCronExpression() {
        val now = Instant.now()
        // expire 된 것들 확인
        val expiredBanners = findAllExpiredBanners(now)

        // 새로운 테이블에 복제
        expiredBannerBulkInsert(expiredBanners)

        // 기존 테이블에서 삭제
        bulkDeleteBanners(expiredBanners)
    }

    private fun findAllExpiredBanners(now: Instant): List<ExpiredTargetBanner> {
        return db
            .select(BANNER.ID, BANNER.LINK, BANNER.IMAGE_ADDRESS, BANNER.EXPIRED_AT)
            .from(BANNER)
            .where(BANNER.EXPIRED_AT.isNotNull
                .and(BANNER.EXPIRED_AT.lessThan(LocalDateTime.ofInstant(now, ZONE_ID)))
            )
            .fetch()
            .into(ExpiredTargetBanner::class.java)
            .toList()
    }

    private fun expiredBannerBulkInsert(expireTargetBanners: List<ExpiredTargetBanner>) {
        val expiredBanners = ArrayList<ExpiredBannerRecord>()
        expireTargetBanners.forEach {banner ->
            val expiredBannerRecord = ExpiredBannerRecord().apply {
                this.link = banner.link
                this.imageAddress = banner.imageAddress
                this.expiredAt = banner.expiredAt
            }
            expiredBanners.add(expiredBannerRecord)
        }
        db.batchInsert(expiredBanners).execute()
    }

    private fun bulkDeleteBanners(expiredBanners: List<ExpiredTargetBanner>) {
        val deletedBannerIds = expiredBanners.map { it.id }
        db.deleteFrom(BANNER).where(BANNER.ID.`in`(deletedBannerIds))
    }

    data class ExpiredTargetBanner (
        val id: Long,
        val link: String,
        val imageAddress: String,
        val expiredAt: LocalDateTime
    )

    companion object {
        val ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")
    }
}
