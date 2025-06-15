const readlineSync = require("readline-sync");

class ConsoleUI {
    constructor(studentService) {
        this.studentService = studentService;
    }

    start() {
        let exit = false;
        while (!exit) {
            console.log("\nStudent Management");
            console.log("1. Add Student");
            console.log("2. View Student");
            console.log("3. Update Student");
            console.log("4. Delete Student");
            console.log("5. List Students");
            console.log("0. Exit");
            try {
                let answer = readlineSync.question("Choose an option: ");
                switch (answer.trim()) {
                    case "1":
                        this.addStudent();
                        break;
                    case "2":
                        this.viewStudent();
                        break;
                    case "3":
                        this.updateStudent();
                        break;
                    case "4":
                        this.deleteStudent();
                        break;
                    case "5":
                        this.listStudents();
                        break;
                    case "0":
                        exit = true;
                        break;
                    default:
                        console.log("Invalid option.");
                }
            } catch (error) {
                console.error("Error:", error.message);
            }
        }
    }

    readStudentIdUserInput() {
        let idInput = readlineSync.question("Enter student ID: ");
        const id = Number(idInput);
        if (isNaN(id)) {
            console.log("Invalid ID. Please enter a number.");
            return this.readStudentIdUserInput();
        }
        return id;
    }

    addStudent() {
        const firstName = readlineSync.question("First name: ");
        const lastName = readlineSync.question("Last name: ");
        const grade = Number(readlineSync.question("Grade: "));
        const student = {
            id: -1, // ID will be generated
            firstName,
            lastName,
            grade,
        };
        this.studentService.addStudent(student);
        console.log("Student added.");
    }

    viewStudent() {
        const id = this.readStudentIdUserInput();
        const student = this.studentService.getStudent(id);
        if (student) {
            console.log(student);
        } else {
            console.log("Student not found.");
        }
    }

    updateStudent() {
        const id = this.readStudentIdUserInput();
        const student = this.studentService.getStudent(id);
        if (!student) {
            console.log("Student not found.");
            return;
        }
        const firstName = readlineSync.question(`First name (${student.firstName}): `) || student.firstName;
        const lastName = readlineSync.question(`Last name (${student.lastName}): `) || student.lastName;
        const gradeInput = readlineSync.question(`Grade (${student.grade}): `);
        const grade = gradeInput ? Number(gradeInput) : student.grade;
        this.studentService.updateStudent({ id, firstName, lastName, grade });
        console.log("Student updated.");
    }

    deleteStudent() {
        const id = this.readStudentIdUserInput();
        this.studentService.deleteStudent(id);
        console.log("Student deleted.");
    }

    listStudents() {
        const students = this.studentService.listStudents();
        if (students.length === 0) {
            console.log("No students found.");
        } else {
            students.forEach(s => {
                console.log(`Id: ${s.id}, First: ${s.firstName}, Last: ${s.lastName}, Grade: ${s.grade}`);
            });
        }
    }
}

module.exports = { ConsoleUI };
