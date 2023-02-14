package com.example.redisdemo.service

import com.example.redisdemo.model.dto.StudentDto
import com.example.redisdemo.model.dto.toStudentDto
import com.example.redisdemo.repository.StudentRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    @Cacheable(cacheNames = ["studentCache"])
    fun findAll() = studentRepository.findAll().map { it.toStudentDto() }

    @Cacheable(value = ["studentCache"])
    fun findById(id: UUID): StudentDto = studentRepository.findById(id).toNullable()?.toStudentDto()
        ?: throw RuntimeException("Student with id $id not found")

}

fun <T> Optional<T>.toNullable(): T? = this.orElse(null)