#!/bin/bash

set -e

mkdir -p student-console-app/src/models
mkdir -p student-console-app/src/db
mkdir -p student-console-app/src/services
mkdir -p student-console-app/src/ui
mkdir -p student-console-app/tests

cat > student-console-app/package.json <<'EOF'
{
  "name": "student-console-app",
  "version": "1.0.0",
  "main": "dist/main.js",
  "scripts": {
    "build": "tsc",
    "start": "node dist/main.js",
    "test": "jest"
  },
  "dependencies": {
    "readline-sync": "^1.4.10",
    "uuid": "^9.0.0"
  },
  "devDependencies": {
    "@types/jest": "^29.5.3",
    "@types/readline-sync": "^1.4.4",
    "@types/uuid": "^9.0.7",
    "jest": "^29.7.0",
    "ts-jest": "^29.1.1",
    "ts-mockito": "^2.6.1",
    "typescript": "^5.4.0"
  }
}
EOF

cat > student-console-app/tsconfig.json <<'EOF'
{
  "compilerOptions": {
    "target": "ES2019",
    "module": "commonjs",
    "outDir": "dist",
    "rootDir": "src",
    "strict": true,
    "esModuleInterop": true
  },
  "include": ["src", "tests"]
}
EOF

cat > student-console-app/README.md <<'EOF'
# Student Console App

A TypeScript console application for managing students, demonstrating CRUD operations and unit testing with mocks using ts-mockito.
EOF

cat > student-console-app/src/models/Student.ts <<'EOF'
export interface Student {
    id: string;
    firstName: string;
    lastName: string;
    grade: number;
}
EOF

cat > student-console-app/src/db/IDatabase.ts <<'EOF'
import { Student } from "../models/Student";

export interface IDatabase {
    createStudent(student: Student): void;
    getStudent(id: string): Student | undefined;
    updateStudent(student: Student): void;
    deleteStudent(id: string): void;
    getAllStudents(): Student[];
}
EOF

cat > student-console-app/src/db/InMemoryDatabase.ts <<'EOF'
import { IDatabase } from "./IDatabase";
import { Student } from "../models/Student";

export class InMemoryDatabase implements IDatabase {
    private students = new Map<string, Student>();

    createStudent(student: Student): void {
        this.students.set(student.id, student);
    }

    getStudent(id: string): Student | undefined {
        return this.students.get(id);
    }

    updateStudent(student: Student): void {
        this.students.set(student.id, student);
    }

    deleteStudent(id: string): void {
        this.students.delete(id);
    }

    getAllStudents(): Student[] {
        return Array.from(this.students.values());
    }
}
EOF

cat > student-console-app/src/services/StudentService.ts <<'EOF'
import { IDatabase } from "../db/IDatabase";
import { Student } from "../models/Student";

export class StudentService {
    constructor(private db: IDatabase) {}

    addStudent(student: Student): void {
        this.db.createStudent(student);
    }

    getStudent(id: string): Student | undefined {
        return this.db.getStudent(id);
    }

    updateStudent(student: Student): void {
        this.db.updateStudent(student);
    }

    deleteStudent(id: string): void {
        this.db.deleteStudent(id);
    }

    listStudents(): Student[] {
        return this.db.getAllStudents();
    }
}
EOF

cat > student-console-app/src/ui/ConsoleUI.ts <<'EOF'
import { StudentService } from "../services/StudentService";
import { Student } from "../models/Student";
import * as readlineSync from "readline-sync";
import { v4 as uuidv4 } from "uuid";

export class ConsoleUI {
    constructor(private studentService: StudentService) {}

    start() {
        let exit = false;
        while (!exit) {
            console.log("\\nStudent Management");
            console.log("1. Add Student");
            console.log("2. View Student");
            console.log("3. Update Student");
            console.log("4. Delete Student");
            console.log("5. List Students");
            console.log("0. Exit");
            const choice = readlineSync.question("Choose an option: ");

            switch (choice) {
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
        }
    }

    private addStudent() {
        const firstName = readlineSync.question("First name: ");
        const lastName = readlineSync.question("Last name: ");
        const grade = Number(readlineSync.question("Grade: "));
        const student: Student = {
            id: uuidv4(),
            firstName,
            lastName,
            grade,
        };
        this.studentService.addStudent(student);
        console.log("Student added.");
    }

    private viewStudent() {
        const id = readlineSync.question("Student ID: ");
        const student = this.studentService.getStudent(id);
        if (student) {
            console.log(student);
        } else {
            console.log("Student not found.");
        }
    }

    private updateStudent() {
        const id = readlineSync.question("Student ID: ");
        const student = this.studentService.getStudent(id);
        if (!student) {
            console.log("Student not found.");
            return;
        }
        const firstName = readlineSync.question(\`First name (\${student.firstName}): \`) || student.firstName;
        const lastName = readlineSync.question(\`Last name (\${student.lastName}): \`) || student.lastName;
        const gradeInput = readlineSync.question(\`Grade (\${student.grade}): \`);
        const grade = gradeInput ? Number(gradeInput) : student.grade;
        this.studentService.updateStudent({ id, firstName, lastName, grade });
        console.log("Student updated.");
    }

    private deleteStudent() {
        const id = readlineSync.question("Student ID: ");
        this.studentService.deleteStudent(id);
        console.log("Student deleted.");
    }

    private listStudents() {
        const students = this.studentService.listStudents();
        if (students.length === 0) {
            console.log("No students found.");
        } else {
            students.forEach(s => {
                console.log(\`\${s.id}: \${s.firstName} \${s.lastName}, Grade: \${s.grade}\`);
            });
        }
    }
}
EOF

cat > student-console-app/src/main.ts <<'EOF'
import { InMemoryDatabase } from "./db/InMemoryDatabase";
import { StudentService } from "./services/StudentService";
import { ConsoleUI } from "./ui/ConsoleUI";

const db = new InMemoryDatabase();
const service = new StudentService(db);
const ui = new ConsoleUI(service);

ui.start();
EOF

cat > student-console-app/tests/StudentService.test.ts <<'EOF'
import { StudentService } from "../src/services/StudentService";
import { IDatabase } from "../src/db/IDatabase";
import { Student } from "../src/models/Student";
import { mock, instance, verify, when } from "ts-mockito";

describe("StudentService", () => {
    let dbMock: IDatabase;
    let dbInstance: IDatabase;
    let service: StudentService;

    beforeEach(() => {
        dbMock = mock<IDatabase>();
        dbInstance = instance(dbMock);
        service = new StudentService(dbInstance);
    });

    it("should add a student", () => {
        const student: Student = { id: "1", firstName: "A", lastName: "B", grade: 90 };
        service.addStudent(student);
        verify(dbMock.createStudent(student)).once();
    });

    it("should get a student", () => {
        const student: Student = { id: "1", firstName: "A", lastName: "B", grade: 90 };
        when(dbMock.getStudent("1")).thenReturn(student);
        const result = service.getStudent("1");
        expect(result).toBe(student);
        verify(dbMock.getStudent("1")).once();
    });

    it("should update a student", () => {
        const student: Student = { id: "1", firstName: "A", lastName: "B", grade: 90 };
        service.updateStudent(student);
        verify(dbMock.updateStudent(student)).once();
    });

    it("should delete a student", () => {
        service.deleteStudent("1");
        verify(dbMock.deleteStudent("1")).once();
    });

    it("should list students", () => {
        const students: Student[] = [
            { id: "1", firstName: "A", lastName: "B", grade: 90 }
        ];
        when(dbMock.getAllStudents()).thenReturn(students);
        const result = service.listStudents();
        expect(result).toBe(students);
        verify(dbMock.getAllStudents()).once();
    });
});
EOF

echo "Scaffold complete! Now run:"
echo "cd student-console-app"
echo "npm install"
echo "npm run build"
echo "npm start"
echo "npm test"