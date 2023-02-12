package com.example.redisdemo.model.dto

import com.example.redisdemo.model.entity.Student
import java.util.UUID

data class StudentDto(
    val id: UUID,
    val name: String,
    val age: Int,
    val university: UniversityDto
)

fun Student.toStudentDto() =
    StudentDto(
        id = requireNotNull(this.id),
        name = this.name,
        age = this.age,
        university = this.university.toUniversityDto()
    )
