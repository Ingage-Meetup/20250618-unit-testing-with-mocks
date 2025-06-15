const { InMemoryDatabase } = require("./db/InMemoryDatabase");
const { StudentService } = require("./services/StudentService");
// You will need to convert ConsoleUI to JS as well if you want to use it
const { ConsoleUI } = require("./ui/ConsoleUI");

const db = new InMemoryDatabase();
const service = new StudentService(db);
const ui = new ConsoleUI(service);

ui.start();