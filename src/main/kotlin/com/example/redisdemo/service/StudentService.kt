package com.example.redisdemo.service

import com.example.redisdemo.model.dto.StudentDto
import com.example.redisdemo.model.dto.toStudentDto
import com.example.redisdemo.model.entity.Student
import com.example.redisdemo.repository.StudentRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
    fun findAll(): List<StudentDto> =
        studentRepository.findAll()
            .map { it.toStudentDto() }

    fun findById(id: UUID): StudentDto =
        studentRepository.findById(id).toNullable()?.toStudentDto()
            ?: throw RuntimeException("Student with id $id not found")

}

fun <T> Optional<T>.toNullable(): T? = this.orElse(null)