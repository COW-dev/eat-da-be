package com.mjucow.eatda.domain.store.service.query.dto

data class Categories(
    val categoryList: List<CategoryDto>
) : ArrayList<CategoryDto>(categoryList)
