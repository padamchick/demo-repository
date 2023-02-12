package com.example.redisdemo.repository

import com.example.redisdemo.model.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StudentRepository : JpaRepository<Student, UUID> {
}