package com.example.redisdemo

import com.example.redisdemo.model.entity.Student
import com.example.redisdemo.model.entity.University
import com.example.redisdemo.repository.StudentRepository
import com.example.redisdemo.repository.UniversityRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RedisDemoApplication {
    @Bean
    fun run(
            universityRepository: UniversityRepository,
            studentRepository: StudentRepository
    ) = CommandLineRunner {
        if(universityRepository.count() == 0L) {
            val agh = universityRepository.save(University(name = "AGH"))
            val uj = universityRepository.save(University(name = "UJ"))
            studentRepository.save(Student(name = "Jarek", age = 32, university = agh))
            studentRepository.save(Student(name = "Jacek", age = 32, university = agh))
            studentRepository.save(Student(name = "Radek", age = 32, university = uj))
            studentRepository.save(Student(name = "Maciek", age = 32, university = uj))
        }

    }
}

fun main(args: Array<String>) {
    runApplication<RedisDemoApplication>(*args)
}
