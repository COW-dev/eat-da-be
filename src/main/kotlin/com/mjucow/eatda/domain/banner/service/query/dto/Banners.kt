package com.mjucow.eatda.domain.banner.service.query.dto

data class Banners(
    val bannerList: List<BannerDto>,
) : ArrayList<BannerDto>(bannerList)
