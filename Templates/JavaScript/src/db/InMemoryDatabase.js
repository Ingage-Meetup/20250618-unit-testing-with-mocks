class InMemoryDatabase {
    constructor() {
        this.students = new Map();
    }

    createStudent(student) {
        const nextId = this.students.size > 0
            ? Math.max(...Array.from(this.students.keys())) + 1
            : 0;
        student.id = nextId;
        this.students.set(student.id, student);
        return student;
    }

    getStudent(id) {
        return this.students.get(id);
    }

    updateStudent(student) {
        this.students.set(student.id, student);
    }

    deleteStudent(id) {
        this.students.delete(id);
    }

    getAllStudents() {
        return Array.from(this.students.values());
    }
}

module.exports = { InMemoryDatabase };
