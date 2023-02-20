package com.example.redisdemo.controller

import com.example.redisdemo.model.dto.toUniversityDto
import com.example.redisdemo.repository.UniversityRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/universities")
class UniversityController(private val universityRepository: UniversityRepository) {
    @GetMapping
    fun findAll() = universityRepository.findAll().map { it.toUniversityDto() }
}