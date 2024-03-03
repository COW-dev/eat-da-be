package com.mjucow.eatda.domain.curation.service.query.dto

data class Curations(
    val curationList: List<CurationDto>,
) : ArrayList<CurationDto>(curationList)
