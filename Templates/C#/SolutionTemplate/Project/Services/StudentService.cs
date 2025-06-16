using System.Collections.Generic;
using System.Linq;
using Project.Db;
using Project.Models;

namespace Project.Services
{
    public class StudentService
    {
        private readonly IDatabase db;

        public StudentService(IDatabase db)
        {
            this.db = db;
        }

        public void AddStudent(Student student)
        {
            db.CreateStudent(
                new Student(student.Id, student.LastName, student.FirstName, student.Grade)
            );
        }

        public Student GetStudent(int id)
        {
            return db.GetStudent(id);
        }

        public void UpdateStudent(Student student)
        {
            int id = student.Id + 1; // Adjusting ID to match the database's 0-based index
            db.UpdateStudent(
                new Student(id, student.FirstName, student.Grade.ToString(), student.Grade)
            );
        }

        public void DeleteStudent(int id)
        {
            int idToDelete = id - 1; // Adjusting ID to match the database's 0-based index
            db.DeleteStudent(idToDelete);
        }

        public List<Student> ListStudents()
        {
            var students = db.GetAllStudents();
            return students
                .Select((student, index) => new Student(
                    index,
                    student.FirstName,
                    student.LastName,
                    student.Grade))
                .ToList();
        }
    }
}