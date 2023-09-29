package com.mjucow.eatda.domain.notice.entity

import com.mjucow.eatda.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "notice")
class Notice(
    @Column(nullable = false) var title: String,
    @Column(nullable = false) var content: String,
) : BaseEntity()
