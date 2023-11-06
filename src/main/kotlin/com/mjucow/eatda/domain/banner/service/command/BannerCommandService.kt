package com.mjucow.eatda.domain.banner.service.command

import com.mjucow.eatda.domain.banner.entity.Banner
import com.mjucow.eatda.domain.banner.service.command.dto.CreateBannerCommand
import com.mjucow.eatda.domain.banner.service.command.dto.UpdateBannerCommand
import com.mjucow.eatda.persistence.banner.BannerRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@Transactional
class BannerCommandService(
    private val repository: BannerRepository,
) {
    fun create(command: CreateBannerCommand): Long {
        return repository.save(Banner(command.link, command.imageAddress, command.expiredAt)).id
    }

    fun update(bannerId: Long, command: UpdateBannerCommand) {
        val (newLink, newImageAddress, newExpiredAt) = command
        val updatedBanner = repository.getReferenceById(bannerId).apply {
            link = newLink
            imageAddress = newImageAddress
            expiredAt = newExpiredAt
        }

        repository.save(updatedBanner)
    }

    fun deleteById(bannerId: Long) {
        val banner = repository.findByIdOrNull(bannerId) ?: return

        repository.delete(banner)
    }
}
