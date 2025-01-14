package pl.poreba.kamil.employeeTimeTracker.employees.repositories.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:latest:///db?TC_INITSCRIPT=file:src/main/resources/schema.sql"
})
@JdbcTest
class EmployeeRepositoryImplTest {

    private EmployeeRepository repository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        repository = new EmployeeRepositoryImpl(this.jdbcTemplate);
    }

    private Employee mockEmployee;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1)
                .firstName("Jan")
                .lastName("Kowalski")
                .position("Programista")
                .email("jan.kowalski@test.pl")
                .build();
    }

    @Test
    void testAddEmployee_whenAllDataCorrect_thenNotThrow() {
        assertDoesNotThrow(() -> repository.addEmployee(mockEmployee));
    }

    @Test
    void testAddEmployee_whenDataNoCorrect_theThrow() {
        assertThrows(
                DataAccessException.class,
                () -> repository.addEmployee(new Employee())
        );
    }

    @Test
    void testAddEmployee_whenNoLastNameSet_thenTrow() {
        Employee partialFillEmployee = Employee.builder()
                .firstName("Jan")
                .email("test@test.com")
                .position("Sprzątacz")
                .build();
        assertThrows(
                DataAccessException.class,
                () -> repository.addEmployee(partialFillEmployee)
        );
    }

    @Test
    void testAddEmployee_whenNoFirstNameSet_thenTrow() {
        Employee partialFillEmployee = Employee.builder()
                .lastName("Kowalski")
                .email("test@test.com")
                .position("Sprzątacz")
                .build();
        assertThrows(
                DataAccessException.class,
                () -> repository.addEmployee(partialFillEmployee)
        );
    }

    @Test
    void testAddEmployee_whenNoEmailSet_thenTrow() {
        Employee partialFillEmployee = Employee.builder()
                .lastName("Kowalski")
                .firstName("Jan")
                .position("Sprzątacz")
                .build();
        assertDoesNotThrow(
                () -> repository.addEmployee(partialFillEmployee)
        );
    }

    @Test
    void testAddEmployee_whenNoPositionSet_thenTrow() {
        Employee partialFillEmployee = Employee.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@test.com")
                .build();
        assertDoesNotThrow(
                () -> repository.addEmployee(partialFillEmployee)
        );
    }

    @Test
    void testGetAllEmployees_whenOneEmployeesAdded_thenReturnListNotEmpty() {
        repository.addEmployee(mockEmployee);
        List<Employee> list = repository.getAllEmployees();
        assertEquals(list.isEmpty(),false);
    }

    @Test
    void testGetAllEmployees_whenNoEmployeesInDatabase_thenReturnEmptyList() {
        List<Employee> list = repository.getAllEmployees();
        assertEquals(list.isEmpty(),true);
    }

    @Test
    void testGetEmployee_whenOneEmployeeInDatabase_thenReturnEmployee() {
        repository.addEmployee(mockEmployee);
        assertDoesNotThrow(
                () -> repository.getEmployeeById(1)
        );
        Employee employee = repository.getEmployeeById(1);
        assertNotNull(employee);
    }

    @Test
    void testGetEmployee_whenNoEmployeeInDataBase_thenThrow() {
        assertThrows(
                DataAccessException.class,
                () -> repository.getEmployeeById(1)
        );
    }

    @Test
    void testGetEmployee_whenOneEmployeeInDatabaseAndWrongId_thenThrow() {
        repository.addEmployee(mockEmployee);
        assertThrows(
                DataAccessException.class,
                () -> repository.getEmployeeById(3)
        );
    }

    @Test
    void testRemoveEmployee_whenEmployeeInDatabaseExist_thenNoThrow() {
        repository.addEmployee(mockEmployee);
        assertDoesNotThrow(
                () -> repository.deleteEmployee(1)
        );
    }

    @Test
    void testRemoveEmployee_whenEmployeeInDatabaseNotExist_thenThrow() {
        repository.addEmployee(mockEmployee);
        repository.deleteEmployee(4);
    }
}