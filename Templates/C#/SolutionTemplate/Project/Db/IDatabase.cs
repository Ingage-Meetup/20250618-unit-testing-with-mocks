using System.Collections.Generic;
using Project.Models;

namespace Project.Db
{
    public interface IDatabase
    {
        Student CreateStudent(Student student);
        Student GetStudent(int id);
        void UpdateStudent(Student student);
        void DeleteStudent(int id);
        List<Student> GetAllStudents();
    }
}
