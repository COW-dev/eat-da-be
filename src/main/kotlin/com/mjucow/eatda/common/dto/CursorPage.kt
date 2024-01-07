package com.mjucow.eatda.common.dto

class CursorPage<T>(
    val contents: List<T>,
    val hasNext: Boolean,
    val nextCursor: String?,
)
