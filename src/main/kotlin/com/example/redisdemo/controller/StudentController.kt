package com.example.redisdemo.controller

import com.example.redisdemo.model.dto.StudentDto
import com.example.redisdemo.service.StudentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentService: StudentService
) {
    @GetMapping
    fun findAll(): List<StudentDto> = studentService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): StudentDto = studentService.findById(id)
}