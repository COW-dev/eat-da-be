package com.mjucow.eatda.scheduled

import com.mjucow.eatda.jooq.tables.Banner.BANNER
import com.mjucow.eatda.jooq.tables.records.ExpiredBannerRecord
import org.jooq.DSLContext
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


@Component
class ExpiredBannerBatchJob(
    private val db: DSLContext,
    private val transactionTemplate: TransactionTemplate,
) {
    @Scheduled(cron = "0 0 22 * * *", zone = "Asia/Seoul") // 매일 오후 10시 동작
    fun scheduleTaskUsingCronExpression() {
        val now = Instant.now()
        transactionTemplate.execute {
            val expiredBanners = findAllExpiredBanners(now)
            if (expiredBanners.isEmpty()) {
                return@execute
            }

            expiredBannerBulkInsert(expiredBanners)

            bulkDeleteBanners(expiredBanners)
        }
    }

    private fun findAllExpiredBanners(now: Instant): List<ExpiredTargetBanner> {
        return db
            .select(BANNER.ID, BANNER.LINK, BANNER.IMAGE_ADDRESS, BANNER.EXPIRED_AT)
            .from(BANNER)
            .where(BANNER.EXPIRED_AT.isNotNull
                .and(BANNER.EXPIRED_AT.lessOrEqual(LocalDateTime.ofInstant(now, ZONE_ID)))
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
        db.deleteFrom(BANNER).where(BANNER.ID.`in`(deletedBannerIds)).execute()
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
