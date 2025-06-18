using System;
using System.Collections.Generic;
using Project.Models;
using Project.Services;

namespace Project.UI
{
    public class ConsoleUI
    {
        private readonly StudentService studentService;

        public ConsoleUI(StudentService studentService)
        {
            this.studentService = studentService;
        }

        public void Start()
        {
            bool exit = false;
            while (!exit)
            {
                Console.WriteLine("\nStudent Management");
                Console.WriteLine("1. Add Student");
                Console.WriteLine("2. View Student");
                Console.WriteLine("3. Update Student");
                Console.WriteLine("4. Delete Student");
                Console.WriteLine("5. List Students");
                Console.WriteLine("0. Exit");
                Console.Write("Choose an option: ");
                string choice = Console.ReadLine();

                try
                {
                    switch (choice)
                    {
                        case "1":
                            AddStudent();
                            break;
                        case "2":
                            ViewStudent();
                            break;
                        case "3":
                            UpdateStudent();
                            break;
                        case "4":
                            DeleteStudent();
                            break;
                        case "5":
                            ListStudents();
                            break;
                        case "0":
                            exit = true;
                            break;
                        default:
                            Console.WriteLine("Invalid option.");
                            break;
                    }
                }
                catch (Exception error)
                {
                    Console.Error.WriteLine("Error: " + error.Message);
                }
            }
        }

        private int ReadStudentIdUserInput()
        {
            Console.Write("Enter student ID: ");
            string idInput = Console.ReadLine();
            if (int.TryParse(idInput, out int id))
            {
                return id;
            }
            else
            {
                Console.WriteLine("Invalid ID. Please enter a number.");
                return ReadStudentIdUserInput();
            }
        }

        private void AddStudent()
        {
            Console.Write("First name: ");
            string firstName = Console.ReadLine();
            Console.Write("Last name: ");
            string lastName = Console.ReadLine();
            Console.Write("Grade: ");
            int grade = int.Parse(Console.ReadLine());
            Student student = new Student(-1, firstName, lastName, grade); // ID will be generated
            studentService.AddStudent(student);
            Console.WriteLine("Student added.");
        }

        private void ViewStudent()
        {
            int id = ReadStudentIdUserInput();
            Student student = studentService.GetStudent(id);
            if (student != null)
            {
                Console.WriteLine(student);
            }
            else
            {
                Console.WriteLine("Student not found.");
            }
        }

        private void UpdateStudent()
        {
            int id = ReadStudentIdUserInput();
            Student student = studentService.GetStudent(id);
            if (student == null)
            {
                Console.WriteLine("Student not found.");
                return;
            }
            Console.Write($"First name ({student.FirstName}): ");
            string firstNameInput = Console.ReadLine();
            string firstName = string.IsNullOrEmpty(firstNameInput) ? student.FirstName : firstNameInput;
            Console.Write($"Last name ({student.LastName}): ");
            string lastNameInput = Console.ReadLine();
            string lastName = string.IsNullOrEmpty(lastNameInput) ? student.LastName : lastNameInput;
            Console.Write($"Grade ({student.Grade}): ");
            string gradeInput = Console.ReadLine();
            int grade = string.IsNullOrEmpty(gradeInput) ? student.Grade : int.Parse(gradeInput);
            Student updatedStudent = new Student(id, firstName, lastName, grade);
            studentService.UpdateStudent(updatedStudent);
            Console.WriteLine("Student updated.");
        }

        private void DeleteStudent()
        {
            int id = ReadStudentIdUserInput();
            studentService.DeleteStudent(id);
            Console.WriteLine("Student deleted.");
        }

        private void ListStudents()
        {
            List<Student> students = studentService.ListStudents();
            if (students.Count == 0)
            {
                Console.WriteLine("No students found.");
            }
            else
            {
                foreach (Student s in students)
                {
                    Console.WriteLine($"Id: {s.Id}, First: {s.FirstName}, Last: {s.LastName}, Grade: {s.Grade}");
                }
            }
        }
    }
}