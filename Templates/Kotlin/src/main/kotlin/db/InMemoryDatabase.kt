package db

import models.Student

class InMemoryDatabase : IDatabase {
    private val students = mutableMapOf<Int, Student>()

    override fun createStudent(student: Student): Student {
        val nextId = if (students.isNotEmpty()) students.keys.maxOrNull()!! + 1 else 0
        student.id = nextId
        students[student.id] = student
        return student
    }

    override fun getStudent(id: Int): Student? {
        return students[id]
    }

    override fun updateStudent(student: Student) {
        students[student.id] = student
    }

    override fun deleteStudent(id: Int) {
        students.remove(id)
    }

    override fun getAllStudents(): List<Student> {
        return students.values.toList()
    }
}
