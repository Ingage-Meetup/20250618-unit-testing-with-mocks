import db.InMemoryDatabase;
import services.StudentService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        InMemoryDatabase db = new InMemoryDatabase();
        StudentService service = new StudentService(db);
        ConsoleUI ui = new ConsoleUI(service);

        ui.start();
    }
}