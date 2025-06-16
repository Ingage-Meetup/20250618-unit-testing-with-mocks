package services

import db.IDatabase
import models.Student
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

class StudentServiceTest {
    private lateinit var dbMock: IDatabase
    private lateinit var service: StudentService

    @BeforeEach
    fun setUp() {
        dbMock = mock()
        service = StudentService(dbMock)
    }

    @Test
    fun testAddStudent() {
        val student = Student(1, "A", "B", 90)
        service.addStudent(student)
        verify(dbMock, times(1)).createStudent(any())
    }

    @Test
    fun testGetStudent() {
        val student = Student(1, "A", "B", 90)
        whenever(dbMock.getStudent(1)).thenReturn(student)
        val result = service.getStudent(1)
        assertEquals(student, result)
        verify(dbMock, times(1)).getStudent(1)
    }

    @Test
    fun testUpdateStudent() {
        val student = Student(1, "A", "B", 90)
        service.updateStudent(student)
        verify(dbMock, times(1)).updateStudent(any())
    }

    @Test
    fun testDeleteStudent() {
        service.deleteStudent(1)
        verify(dbMock, times(1)).deleteStudent(any())
    }

    @Test
    fun testListStudents() {
        val students = listOf(
            Student(1, "A", "B", 90),
            Student(2, "first2", "last2", 95)
        )
        whenever(dbMock.getAllStudents()).thenReturn(students)
        val result = service.listStudents()
        assertEquals(2, result.size)
        verify(dbMock, times(1)).getAllStudents()
    }
}