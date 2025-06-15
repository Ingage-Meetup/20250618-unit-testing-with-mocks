const { StudentService } = require("../../src/services/StudentService");

describe("StudentService", () => {
    let dbMock;
    let service;

    beforeEach(() => {
        // Mock the database methods
        dbMock = {
            createStudent: jest.fn(),
            getStudent: jest.fn(),
            updateStudent: jest.fn(),
            deleteStudent: jest.fn(),
            getAllStudents: jest.fn()
        };

        // Create an instance of StudentService with the mocked database
        service = new StudentService(dbMock);
    });

    it("should add a student", () => {
        const student = { id: 1, firstName: "A", lastName: "B", grade: 90 };

        // Act - call the method to add a student
        service.addStudent(student);

        // Assert - check if the database method was called with the correct parameters
        expect(dbMock.createStudent).toHaveBeenCalled();
    });

    it("should get a student", () => {
        const student = { id: 1, firstName: "A", lastName: "B", grade: 90 };

        // Mock the database response
        dbMock.getStudent.mockReturnValue(student);

        // Act - call the method to get a student
        const result = service.getStudent(1);

        // Assert - check if the returned student matches the mock and if the database method was called
        expect(result).toBe(student);
        expect(dbMock.getStudent).toHaveBeenCalled();
    });

    it("should update a student", () => {
        const student = { id: 1, firstName: "A", lastName: "B", grade: 90 };

        // Act - call the method to update a student
        service.updateStudent(student);

        // Assert - check if the database method was called
        expect(dbMock.updateStudent).toHaveBeenCalled();
    });

    it("should delete a student", () => {
        // Act - call the method to delete a student
        service.deleteStudent(1);

        // Assert - check if the database method was called
        expect(dbMock.deleteStudent).toHaveBeenCalled();
    });

    it("should list students", () => {
        const students = [
            { id: 1, firstName: "A", lastName: "B", grade: 90 },
            { id: 2, firstName: "first2", lastName: "last2", grade: 95 }
        ];

        // Mock the database response
        dbMock.getAllStudents.mockReturnValue(students);

        // Act - call the method to list students
        const result = service.listStudents();

        // Assert - check if the returned students match the mock and if the database method was called
        expect(result).toHaveLength(2);
        expect(dbMock.getAllStudents).toHaveBeenCalled();
    });
});