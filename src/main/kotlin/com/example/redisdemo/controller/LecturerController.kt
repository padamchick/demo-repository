package com.example.redisdemo.controller

import com.example.redisdemo.service.LecturerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lecturers")
class LecturerController(
    private val lecturerService: LecturerService
) {
    @GetMapping
    fun findAll() = lecturerService.findAll()
}