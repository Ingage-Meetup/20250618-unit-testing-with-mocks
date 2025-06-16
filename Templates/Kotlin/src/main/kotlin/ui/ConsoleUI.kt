package ui

import models.Student
import services.StudentService
import java.util.Scanner

class ConsoleUI(private val studentService: StudentService) {
    private val scanner = Scanner(System.`in`)

    fun start() {
        var exit = false
        while (!exit) {
            println("\nStudent Management")
            println("1. Add Student")
            println("2. View Student")
            println("3. Update Student")
            println("4. Delete Student")
            println("5. List Students")
            println("0. Exit")
            print("Choose an option: ")
            val choice = scanner.nextLine()

            try {
                when (choice) {
                    "1" -> addStudent()
                    "2" -> viewStudent()
                    "3" -> updateStudent()
                    "4" -> deleteStudent()
                    "5" -> listStudents()
                    "0" -> exit = true
                    else -> println("Invalid option.")
                }
            } catch (error: Exception) {
                System.err.println("Error: ${error.message}")
            }
        }
    }

    private fun readStudentIdUserInput(): Int {
        print("Enter student ID: ")
        val idInput = scanner.nextLine()
        return try {
            idInput.toInt()
        } catch (e: NumberFormatException) {
            println("Invalid ID. Please enter a number.")
            readStudentIdUserInput()
        }
    }

    private fun addStudent() {
        print("First name: ")
        val firstName = scanner.nextLine()
        print("Last name: ")
        val lastName = scanner.nextLine()
        print("Grade: ")
        val grade = scanner.nextLine().toInt()
        val student = Student(-1, firstName, lastName, grade) // ID will be generated
        studentService.addStudent(student)
        println("Student added.")
    }

    private fun viewStudent() {
        val id = readStudentIdUserInput()
        val student = studentService.getStudent(id)
        if (student != null) {
            println(student)
        } else {
            println("Student not found.")
        }
    }

    private fun updateStudent() {
        val id = readStudentIdUserInput()
        val student = studentService.getStudent(id)
        if (student == null) {
            println("Student not found.")
            return
        }
        print("First name (${student.firstName}): ")
        val firstNameInput = scanner.nextLine()
        val firstName = if (firstNameInput.isEmpty()) student.firstName else firstNameInput
        print("Last name (${student.lastName}): ")
        val lastNameInput = scanner.nextLine()
        val lastName = if (lastNameInput.isEmpty()) student.lastName else lastNameInput
        print("Grade (${student.grade}): ")
        val gradeInput = scanner.nextLine()
        val grade = if (gradeInput.isEmpty()) student.grade else gradeInput.toInt()
        val updatedStudent = Student(id, firstName, lastName, grade)
        studentService.updateStudent(updatedStudent)
        println("Student updated.")
    }

    private fun deleteStudent() {
        val id = readStudentIdUserInput()
        studentService.deleteStudent(id)
        println("Student deleted.")
    }

    private fun listStudents() {
        val students = studentService.listStudents()
        if (students.isEmpty()) {
            println("No students found.")
        } else {
            for (s in students) {
                println("Id: ${s.id}, First: ${s.firstName}, Last: ${s.lastName}, Grade: ${s.grade}")
            }
        }
    }
}