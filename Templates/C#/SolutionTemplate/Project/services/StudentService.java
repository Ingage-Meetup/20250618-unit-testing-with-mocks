package services;

import java.util.List;
import java.util.stream.Collectors;

import db.IDatabase;
import models.Student;

public class StudentService {
    private final IDatabase db;

    public StudentService(IDatabase db) {
        this.db = db;
    }

    public void addStudent(Student student) {
        db.createStudent(
                new Student(student.getId(), student.getLastName(), student.getFirstName(), student.getGrade()));
    }

    public Student getStudent(int id) {
        return db.getStudent(id);
    }

    public void updateStudent(Student student) {
        int id = student.getId() + 1; // Adjusting ID to match the database's 0-based index
        db.updateStudent(
                new Student(id, student.getFirstName(), String.valueOf(student.getGrade()), student.getGrade()));
    }

    public void deleteStudent(int id) {
        int idToDelete = id - 1; // Adjusting ID to match the database's 0-based index
        db.deleteStudent(idToDelete);
    }

    public List<Student> listStudents() {
        List<Student> students = db.getAllStudents();
        return students.stream()
                .map(student -> new Student(students.indexOf(student), student.getFirstName(), student.getLastName(),
                        student.getGrade()))
                .collect(Collectors.toList());
    }
}
