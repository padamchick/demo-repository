package com.example.redisdemo.model.dto

import com.example.redisdemo.model.entity.Lecturer
import java.io.Serializable
import java.util.UUID

data class LecturerWithUniversitiesDto(
    val id: UUID,
    val name: String,
    val universities: List<UniversityDto>
) : Serializable

fun Lecturer.toLecturerWithUniversitiesDto() =
    LecturerWithUniversitiesDto(
        id = requireNotNull(this.id),
        name = this.name,
        universities = this.universities.map { it.toUniversityDto() }
    )
