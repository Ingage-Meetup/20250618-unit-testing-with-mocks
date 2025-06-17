# Unit Testing With Mocks

## Objectives
This exercise will get you working hands-on with unit test frameworks like Jest and ts-mockito (TypeScript), Jest and Jest mocks (JavaScript), JUnit and Mockito (Java), JUnit and Mockito Kotlin (Kotlin), and NUnit and Moq (C#).

We have a functioning application in each language in the repo under the [Templates](./Templates/) directory. Each one is a very simple console application that lets you enter students and grades, and then perform various other CRUD (Create, Read, Update, and Delete) operations on those students. To simulate a database, each solution has a simple in-memory database that the application will interact with. Each solution has a service that performs the "business logic" of the application, and it interacts with the in-memory database. The idea is that in our unit tests, we will mock the database layer and test our service. The solutions have passing unit tests on the service, but as you play with the console application, you will see that there are a number of issues with the service, and even though the unit tests are passing, there are problems in the service code.

The main objective tonight is to learn more about unit testing frameworks and assertions, and also learn about mocking frameworks. So once you have identified some errors in the console application, find the appropriate unit test, and figure out how you can make it fail. You know the application has a problem, so the unit test should not be passing! Then you should be able to go to the service code, identify the problem (they are not very tricky, so please try to focus on the unit tests before debugging and/or fixing the code!), and fix it - and then the unit test should pass.

## Why Mocking with a Framework?
Pick a solution language and explore the mocking framework being used to learn more about mocking, and the benefits of using a mocking library versus implementing your own mocks.

In a nutshell, here are the benefits of using any mocking library:
- **Less Boilerplate:** Mocking libraries automatically generate mock objects, saving you from writing repetitive code.
- **Flexible Behavior:** Easily configure mocks to return specific values, throw exceptions, or verify method calls.
- **Readable Tests:** Test code is more concise and expressive, making intent clearer.
- **Advanced Features:** Support for argument matching, call order verification, and interaction verification.
- **Reduced Maintenance:** Changes to dependencies often require fewer updates to test code.
- **Community Support:** Well-maintained libraries are robust, documented, and widely used, reducing bugs in your tests.

Using a library (like Mockito for Java) helps you focus on testing logic, not on maintaining hand-written test doubles.

## Example: Using Mockito Mocks in JUnit Tests

Suppose you have a `UserService` that depends on a `UserRepository`. With Mockito, you can easily mock the repository:

```java
// Using Mockito
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    @Test
    void testFindUser() {
        UserRepository mockRepo = mock(UserRepository.class);
        when(mockRepo.findById(1)).thenReturn(new User(1, "Alice"));

        UserService service = new UserService(mockRepo);
        User user = service.findUser(1);

        assertEquals("Alice", user.getName());
        verify(mockRepo).findById(1);
    }
}
```

### Without a Mocking Library (Hand-written Fake)

If you were not using a mocking library, you’d have to write your own fake implementation:

```java
// Without Mockito
class FakeUserRepository implements UserRepository {
    @Override
    public User findById(int id) {
        if (id == 1) return new User(1, "Alice");
        return null;
    }
    // Implement other methods as needed...
}

class UserServiceTest {
    @Test
    void testFindUser() {
        UserRepository fakeRepo = new FakeUserRepository();

        UserService service = new UserService(fakeRepo);
        User user = service.findUser(1);

        assertEquals("Alice", user.getName());
        // No easy way to verify method calls or arguments
    }
}
```

**With Mocking Libraries,** you avoid writing boilerplate fakes and get powerful features like method verification and flexible stubbing.


## Solution Templates
See solutions in different languages in the "Templates" directory. Once you decide which language you'd like to use,
simply open that directory in your favorite IDE, and you should be able to run the included unit tests "out of the box".

The recommended IDEs are as follows, but feel free to use whatever IDE you are comfortable with.

-   [C#](Templates/C%23) - [Microsoft Visual Studio](https://visualstudio.microsoft.com/vs/community/)
-   [Java](Templates/Java) - [IntelliJ Idea](https://www.jetbrains.com/idea/download) (Community Edition is fine)
-   [JavaScript](Templates/JavaScript) - [Microsoft Visual Studio Code](https://code.visualstudio.com/)
-   [Kotlin](Templates/Kotlin) - [IntelliJ Idea](https://www.jetbrains.com/idea/download) (Community Edition is fine)
-   [TypeScript](Templates/TypeScript) - [Microsoft Visual Studio Code](https://code.visualstudio.com/)
