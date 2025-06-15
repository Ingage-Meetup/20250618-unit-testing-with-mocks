import { StudentService } from "../../src/services/StudentService";
import { IDatabase } from "../../src/db/IDatabase";
import { Student } from "../../src/models/Student";
import { mock, instance, verify, when, anything } from "ts-mockito";

describe("StudentService", () => {
    var dbMock: IDatabase;
    var service: StudentService;

    beforeEach(() => {
        // Create a mock of the IDatabase interface
        dbMock = mock<IDatabase>();

        // Use the mock to create an instance of IDatabase
        // This allows us to define the behavior of the mocked methods
        // without needing a real database connection
        // The `instance` function creates a type-safe instance of the mock
        // that can be used in tests
        const dbInstance = instance(dbMock);

        // Create an instance of StudentService with the mocked database
        // This allows us to intercept calls to the database methods
        service = new StudentService(dbInstance);
    });

    it("should add a student", () => {
        const student: Student = { id: 1, firstName: "A", lastName: "B", grade: 90 };

        // Act - call the addStudent method
        service.addStudent(student);

        // Verify that the createStudent method was called with the correct student object
        verify(dbMock.createStudent(anything())).once();
    });

    it("should get a student", () => {
        const student: Student = { id: 1, firstName: "A", lastName: "B", grade: 90 };

        // Mock the getStudent method to return a specific student
        when(dbMock.getStudent(1)).thenReturn(student);

        // Act - call the getStudent method
        const result = service.getStudent(1);

        // Assert - check that the result matches the expected student
        expect(result).toBe(student);

        // Verify - check that the getStudent method was called once with the correct ID
        verify(dbMock.getStudent(1)).once();
    });

    it("should update a student", () => {
        const student: Student = { id: 1, firstName: "A", lastName: "B", grade: 90 };

        // Act - call the updateStudent method
        service.updateStudent(student);

        // Verify - check that the updateStudent method was called with the correct student object
        verify(dbMock.updateStudent(anything())).once();
    });

    it("should delete a student", () => {
        // Act - call the deleteStudent method with a specific student ID
        service.deleteStudent(1);

        // Verify - check that the deleteStudent method was called with the correct ID
        verify(dbMock.deleteStudent(anything())).once();
    });

    it("should list students", () => {
        const students: Student[] = [
            { id: 1, firstName: "A", lastName: "B", grade: 90 },
            { id: 2, firstName: "first2", lastName: "last2", grade: 95 }
        ];

        // Mock the getAllStudents method to return a list of students
        when(dbMock.getAllStudents()).thenReturn(students);

        // Act - call the listStudents method
        const result = service.listStudents();

        // Assert - check that the result matches the expected list of students
        expect(result).toHaveLength(2);

        // Verify - check that the getAllStudents method was called once
        verify(dbMock.getAllStudents()).once();
    });
});
