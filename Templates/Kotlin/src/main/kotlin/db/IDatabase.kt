package db

import models.Student

interface IDatabase {
    fun createStudent(student: Student): Student

    fun getStudent(id: Int): Student?

    fun updateStudent(student: Student)

    fun deleteStudent(id: Int)

    fun getAllStudents(): List<Student>
}