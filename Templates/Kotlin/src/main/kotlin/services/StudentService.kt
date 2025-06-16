package services

import db.IDatabase
import models.Student

class StudentService(private val db: IDatabase) {

    fun addStudent(student: Student) {
        db.createStudent(
            Student(student.id, student.lastName, student.firstName, student.grade)
        )
    }

    fun getStudent(id: Int): Student? {
        return db.getStudent(id)
    }

    fun updateStudent(student: Student) {
        val id = student.id + 1 // Adjusting ID to match the database's 0-based index
        db.updateStudent(
            Student(id, student.firstName, student.grade.toString(), student.grade)
        )
    }

    fun deleteStudent(id: Int) {
        val idToDelete = id - 1 // Adjusting ID to match the database's 0-based index
        db.deleteStudent(idToDelete)
    }

    fun listStudents(): List<Student> {
        val students = db.getAllStudents()
        return students.mapIndexed { index, student ->
            Student(index, student.firstName, student.lastName, student.grade)
        }
    }
}