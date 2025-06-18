package db;

import java.util.List;

import models.Student;

public interface IDatabase {
    Student createStudent(Student student);

    Student getStudent(int id);

    void updateStudent(Student student);

    void deleteStudent(int id);

    List<Student> getAllStudents();
}