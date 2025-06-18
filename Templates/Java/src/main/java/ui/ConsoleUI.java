package ui;

import java.util.List;
import java.util.Scanner;

import models.Student;
import services.StudentService;

public class ConsoleUI {
    private final StudentService studentService;
    private final Scanner scanner;

    public ConsoleUI(StudentService studentService) {
        this.studentService = studentService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nStudent Management");
            System.out.println("1. Add Student");
            System.out.println("2. View Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. List Students");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1" -> this.addStudent();
                    case "2" -> this.viewStudent();
                    case "3" -> this.updateStudent();
                    case "4" -> this.deleteStudent();
                    case "5" -> this.listStudents();
                    case "0" -> exit = true;
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception error) {
                System.err.println("Error: " + error.getMessage());
            }
        }
    }

    private int readStudentIdUserInput() {
        System.out.print("Enter student ID: ");
        String idInput = scanner.nextLine();
        try {
            int id = Integer.parseInt(idInput);
            return id;
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
            return this.readStudentIdUserInput();
        }
    }

    private void addStudent() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Grade: ");
        int grade = Integer.parseInt(scanner.nextLine());
        Student student = new Student(-1, firstName, lastName, grade); // ID will be generated
        this.studentService.addStudent(student);
        System.out.println("Student added.");
    }

    private void viewStudent() {
        int id = this.readStudentIdUserInput();
        Student student = this.studentService.getStudent(id);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private void updateStudent() {
        int id = this.readStudentIdUserInput();
        Student student = this.studentService.getStudent(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("First name (" + student.getFirstName() + "): ");
        String firstNameInput = scanner.nextLine();
        String firstName = firstNameInput.isEmpty() ? student.getFirstName() : firstNameInput;
        System.out.print("Last name (" + student.getLastName() + "): ");
        String lastNameInput = scanner.nextLine();
        String lastName = lastNameInput.isEmpty() ? student.getLastName() : lastNameInput;
        System.out.print("Grade (" + student.getGrade() + "): ");
        String gradeInput = scanner.nextLine();
        int grade = gradeInput.isEmpty() ? student.getGrade() : Integer.parseInt(gradeInput);
        Student updatedStudent = new Student(id, firstName, lastName, grade);
        this.studentService.updateStudent(updatedStudent);
        System.out.println("Student updated.");
    }

    private void deleteStudent() {
        int id = this.readStudentIdUserInput();
        this.studentService.deleteStudent(id);
        System.out.println("Student deleted.");
    }

    private void listStudents() {
        List<Student> students = this.studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println("Id: " + s.getId() + ", First: " + s.getFirstName() + ", Last: " + s.getLastName()
                        + ", Grade: " + s.getGrade());
            }
        }
    }
}
