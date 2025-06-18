using System.Collections.Generic;
using System.Linq;
using Project.Models;

namespace Project.Db
{
    public class InMemoryDatabase : IDatabase
    {
        private readonly Dictionary<int, Student> students = new Dictionary<int, Student>();

        public Student CreateStudent(Student student)
        {
            int nextId = students.Any() ? students.Keys.Max() + 1 : 0;
            student.Id = nextId;
            students[student.Id] = student;
            return student;
        }

        public Student GetStudent(int id)
        {
            students.TryGetValue(id, out var student);
            return student;
        }

        public void UpdateStudent(Student student)
        {
            students[student.Id] = student;
        }

        public void DeleteStudent(int id)
        {
            students.Remove(id);
        }

        public List<Student> GetAllStudents()
        {
            return students.Values.ToList();
        }
    }
}
