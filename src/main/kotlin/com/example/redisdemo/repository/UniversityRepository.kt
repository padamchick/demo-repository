package com.example.redisdemo.repository

import com.example.redisdemo.model.entity.University
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UniversityRepository : JpaRepository<University, UUID> {
}