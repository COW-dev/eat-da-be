package com.mjucow.eatda.persistence.banner

import com.mjucow.eatda.domain.banner.entity.Banner
import org.springframework.data.jpa.repository.JpaRepository

interface BannerRepository : JpaRepository<Banner, Long> {
    fun findAllByOrderByIdDesc(): List<Banner>
}
