package com.example.redisdemo.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import org.hibernate.Hibernate
import java.util.UUID

@Entity
data class Lecturer(
    @Id
    @GeneratedValue
    var id: UUID? = null,
    var name: String,

    @ManyToMany
    @JoinTable(
        name = "universities_lecturers",
        joinColumns = [JoinColumn(name = "lecturer_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "university_id", referencedColumnName = "id")]
    )
    var universities: MutableSet<University> = mutableSetOf()
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Lecturer

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}
