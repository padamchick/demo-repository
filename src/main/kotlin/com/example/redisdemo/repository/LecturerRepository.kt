package com.example.redisdemo.repository

import com.example.redisdemo.model.entity.Lecturer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface LecturerRepository : JpaRepository<Lecturer, UUID> {
    @Query(
        """SELECT DISTINCT l FROM Lecturer l
        JOIN FETCH l.universities"""
    )
    override fun findAll(): MutableList<Lecturer>
}