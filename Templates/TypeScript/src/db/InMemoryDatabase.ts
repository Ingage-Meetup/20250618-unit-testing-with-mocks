import { IDatabase } from "./IDatabase";
import { Student } from "../models/Student";

export class InMemoryDatabase implements IDatabase {
    private students = new Map<number, Student>();

    createStudent(student: Student): Student {
        // Find the maximum current id and increment it for the new student
        const nextId = this.students.size > 0
            ? Math.max(...Array.from(this.students.keys())) + 1
            : 0;
        student.id = nextId; // Assign the new id
        this.students.set(student.id, student);
        return student;
    }

    getStudent(id: number): Student | undefined {
        return this.students.get(id);
    }

    updateStudent(student: Student): void {
        this.students.set(student.id, student);
    }

    deleteStudent(id: number): void {
        this.students.delete(id);
    }

    getAllStudents(): Student[] {
        return Array.from(this.students.values());
    }
}
