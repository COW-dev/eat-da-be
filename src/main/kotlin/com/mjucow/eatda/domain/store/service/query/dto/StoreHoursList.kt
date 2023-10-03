package com.mjucow.eatda.domain.store.service.query.dto

data class StoreHoursList(
    val storeHoursDtoList: List<StoreHoursDto>,
) : ArrayList<StoreHoursDto>(storeHoursDtoList)
