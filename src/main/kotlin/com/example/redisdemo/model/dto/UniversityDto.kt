package com.example.redisdemo.model.dto

import com.example.redisdemo.model.entity.University
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.UUID

data class UniversityDto(
    @JsonProperty("id")
    val id: UUID,
    @JsonProperty("name")
    val name: String
) : Serializable

fun University.toUniversityDto() =
    UniversityDto(id = requireNotNull(this.id), name = this.name)
