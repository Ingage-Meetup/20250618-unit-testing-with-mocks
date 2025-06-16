using Project.Db;
using Project.Services;
using Project.UI;

namespace Project
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var db = new InMemoryDatabase();
            var service = new StudentService(db);
            var ui = new ConsoleUI(service);

            ui.Start();
        }
    }
}
