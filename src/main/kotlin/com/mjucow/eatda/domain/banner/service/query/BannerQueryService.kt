package com.mjucow.eatda.domain.banner.service.query

import com.mjucow.eatda.domain.banner.service.query.dto.BannerDto
import com.mjucow.eatda.domain.banner.service.query.dto.Banners
import com.mjucow.eatda.persistence.banner.BannerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BannerQueryService(
    private val repository: BannerRepository,
) {
    fun findAll(): Banners {
        return Banners(repository.findAllByOrderByIdDesc().map(BannerDto::from))
    }

    fun findById(bannerId: Long): BannerDto {
        return BannerDto.from(repository.getReferenceById(bannerId))
    }
}
