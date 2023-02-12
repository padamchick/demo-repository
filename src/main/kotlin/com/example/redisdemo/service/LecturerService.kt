package com.example.redisdemo.service

import com.example.redisdemo.model.dto.toLecturerWithUniversitiesDto
import com.example.redisdemo.repository.LecturerRepository
import org.springframework.stereotype.Service

@Service
class LecturerService(
    private val lecturerRepository: LecturerRepository
) {
    fun findAll() =
        lecturerRepository.findAll().map { it.toLecturerWithUniversitiesDto() }
}