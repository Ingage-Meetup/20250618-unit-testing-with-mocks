import { InMemoryDatabase } from "./db/InMemoryDatabase";
import { StudentService } from "./services/StudentService";
import { ConsoleUI } from "./ui/ConsoleUI";

const db = new InMemoryDatabase();
const service = new StudentService(db);
const ui = new ConsoleUI(service);

ui.start();
