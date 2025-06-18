using System.Collections.Generic;
using Moq;
using NUnit.Framework;
using Project.Db;
using Project.Models;
using Project.Services;
using FluentAssertions;

namespace Project.Test.Services
{
    [TestFixture]
    public class StudentServiceTest
    {
        private Mock<IDatabase> dbMock;
        private StudentService service;

        [SetUp]
        public void SetUp()
        {
            dbMock = new Mock<IDatabase>();
            service = new StudentService(dbMock.Object);
        }

        [Test]
        public void TestAddStudent()
        {
            var student = new Student(1, "A", "B", 90);
            service.AddStudent(student);
            dbMock.Verify(db => db.CreateStudent(It.IsAny<Student>()), Times.Once);
        }

        [Test]
        public void TestGetStudent()
        {
            var student = new Student(1, "A", "B", 90);
            dbMock.Setup(db => db.GetStudent(1)).Returns(student);
            var result = service.GetStudent(1);
            result.Should().Be(student);
            dbMock.Verify(db => db.GetStudent(1), Times.Once);
        }

        [Test]
        public void TestUpdateStudent()
        {
            var student = new Student(1, "A", "B", 90);
            service.UpdateStudent(student);
            dbMock.Verify(db => db.UpdateStudent(It.IsAny<Student>()), Times.Once);
        }

        [Test]
        public void TestDeleteStudent()
        {
            service.DeleteStudent(1);
            dbMock.Verify(db => db.DeleteStudent(It.IsAny<int>()), Times.Once);
        }

        [Test]
        public void TestListStudents()
        {
            var students = new List<Student>
            {
                new Student(1, "A", "B", 90),
                new Student(2, "first2", "last2", 95)
            };
            dbMock.Setup(db => db.GetAllStudents()).Returns(students);
            var result = service.ListStudents();
            result.Count.Should().Be(2);
            dbMock.Verify(db => db.GetAllStudents(), Times.Once);
        }
    }
}