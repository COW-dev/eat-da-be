package com.mjucow.eatda.persistence.curation

import com.mjucow.eatda.domain.curation.entity.Curation
import org.springframework.data.jpa.repository.JpaRepository

interface CurationRepository : JpaRepository<Curation, Long>
