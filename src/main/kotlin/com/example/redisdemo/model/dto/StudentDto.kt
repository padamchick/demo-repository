package com.example.redisdemo.model.dto

import com.example.redisdemo.model.entity.Student
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.cache.annotation.Cacheable
import java.io.Serializable
import java.util.UUID

data class StudentDto(
    @JsonProperty("id")
    val id: UUID = UUID.randomUUID(),
    @JsonProperty("name")
    val name: String,
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("university")
    val university: UniversityDto
): Serializable {
}

fun Student.toStudentDto() =
    StudentDto(
        id = requireNotNull(this.id),
        name = this.name,
        age = this.age,
        university = this.university.toUniversityDto()
    )