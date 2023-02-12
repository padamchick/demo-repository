package com.example.redisdemo.model.dto

import com.example.redisdemo.model.entity.University
import java.util.UUID

data class UniversityDto(
    val id: UUID,
    val name: String
)

fun University.toUniversityDto() =
    UniversityDto(id = requireNotNull(this.id), name = this.name)
