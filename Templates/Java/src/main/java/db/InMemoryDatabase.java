package db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.Student;

public class InMemoryDatabase implements IDatabase {
    private final Map<Integer, Student> students = new HashMap<>();

    @Override
    public Student createStudent(Student student) {
        // Find the maximum current id and increment it for the new student
        int nextId = !students.isEmpty()
                ? students.keySet().stream().max(Integer::compare).get() + 1
                : 0;
        student.setId(nextId); // Assign the new id
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudent(int id) {
        return students.get(id);
    }

    @Override
    public void updateStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public void deleteStudent(int id) {
        students.remove(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return students.values().stream().collect(Collectors.toList());
    }
}
