package com.mjucow.eatda.domain.store.service.query.dto

import com.mjucow.eatda.domain.store.service.query.MenuDto

data class MenuList(val menu: List<MenuDto>) : ArrayList<MenuDto>()
