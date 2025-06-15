import { Student } from "../models/Student";

export interface IDatabase {
    createStudent(student: Student): Student;
    getStudent(id: number): Student | undefined;
    updateStudent(student: Student): void;
    deleteStudent(id: number): void;
    getAllStudents(): Student[];
}
