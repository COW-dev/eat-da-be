package com.mjucow.eatda.domain.store.service.query.dto

data class Stores(
    val storeList: List<StoreDto>,
) : ArrayList<StoreDto>(storeList)
