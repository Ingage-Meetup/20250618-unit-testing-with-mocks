package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.IDatabase;
import models.Student;

public class StudentServiceTest {
    private IDatabase dbMock;
    private StudentService service;

    @BeforeEach
    public void setUp() {
        dbMock = mock(IDatabase.class);
        service = new StudentService(dbMock);
    }

    @Test
    public void testAddStudent() {
        Student student = new Student(1, "A", "B", 90);
        service.addStudent(student);
        verify(dbMock, times(1)).createStudent(any(Student.class));
    }

    @Test
    public void testGetStudent() {
        Student student = new Student(1, "A", "B", 90);
        when(dbMock.getStudent(1)).thenReturn(student);
        Student result = service.getStudent(1);
        assertEquals(student, result);
        verify(dbMock, times(1)).getStudent(1);
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student(1, "A", "B", 90);
        service.updateStudent(student);
        verify(dbMock, times(1)).updateStudent(any(Student.class));
    }

    @Test
    public void testDeleteStudent() {
        service.deleteStudent(1);
        verify(dbMock, times(1)).deleteStudent(anyInt());
    }

    @Test
    public void testListStudents() {
        List<Student> students = Arrays.asList(
                new Student(1, "A", "B", 90),
                new Student(2, "first2", "last2", 95));
        when(dbMock.getAllStudents()).thenReturn(students);
        List<Student> result = service.listStudents();
        assertEquals(2, result.size());
        verify(dbMock, times(1)).getAllStudents();
    }
}
