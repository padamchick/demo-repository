package com.example.redisdemo.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.UUID

@Entity
data class Student(
    @Id
    @GeneratedValue
    var id: UUID? = null,
    var name: String,
    var age: Int,
    @ManyToOne
    @JoinColumn(name = "university_id")
    var university: University
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Student

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , age = $age , university = $university )"
    }
}