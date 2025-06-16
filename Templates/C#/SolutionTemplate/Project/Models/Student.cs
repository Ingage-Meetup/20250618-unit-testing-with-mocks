namespace Project.Models
{
    public class Student
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public int Grade { get; set; }

        public Student(int id, string firstName, string lastName, int grade)
        {
            Id = id;
            FirstName = firstName;
            LastName = lastName;
            Grade = grade;
        }

        public override string ToString()
        {
            return $"Student{{id={Id}, firstName='{FirstName}', lastName='{LastName}', grade={Grade}}}";
        }
    }
}
