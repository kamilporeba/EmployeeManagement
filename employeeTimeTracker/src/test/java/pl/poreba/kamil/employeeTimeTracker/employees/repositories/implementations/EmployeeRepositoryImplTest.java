package pl.poreba.kamil.employeeTimeTracker.employees.repositories.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
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

    private EmployeeDTO mockEmployeeDTO;

    @BeforeEach
    void setUp() {
        mockEmployeeDTO = EmployeeDTO.builder()
                .id(1)
                .firstName("Jan")
                .lastName("Kowalski")
                .position("Programista")
                .email("jan.kowalski@test.pl")
                .build();
    }

    @Test
    void testAddEmployee_whenAllDataCorrect_thenNotThrow() {
        assertDoesNotThrow(() -> repository.addEmployee(mockEmployeeDTO));
    }

    @Test
    void testAddEmployee_whenDataNoCorrect_theThrow() {
        assertThrows(
                DataAccessException.class,
                () -> repository.addEmployee(new EmployeeDTO())
        );
    }

    @Test
    void testAddEmployee_whenNoLastNameSet_thenTrow() {
        EmployeeDTO partialFillEmployeeDTO = EmployeeDTO.builder()
                .firstName("Jan")
                .email("test@test.com")
                .position("Sprzątacz")
                .build();
        assertThrows(
                DataAccessException.class,
                () -> repository.addEmployee(partialFillEmployeeDTO)
        );
    }

    @Test
    void testAddEmployee_whenNoFirstNameSet_thenTrow() {
        EmployeeDTO partialFillEmployeeDTO = EmployeeDTO.builder()
                .lastName("Kowalski")
                .email("test@test.com")
                .position("Sprzątacz")
                .build();
        assertThrows(
                DataAccessException.class,
                () -> repository.addEmployee(partialFillEmployeeDTO)
        );
    }

    @Test
    void testAddEmployee_whenNoEmailSet_thenTrow() {
        EmployeeDTO partialFillEmployeeDTO = EmployeeDTO.builder()
                .lastName("Kowalski")
                .firstName("Jan")
                .position("Sprzątacz")
                .build();
        assertDoesNotThrow(
                () -> repository.addEmployee(partialFillEmployeeDTO)
        );
    }

    @Test
    void testAddEmployee_whenNoPositionSet_thenTrow() {
        EmployeeDTO partialFillEmployeeDTO = EmployeeDTO.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@test.com")
                .build();
        assertDoesNotThrow(
                () -> repository.addEmployee(partialFillEmployeeDTO)
        );
    }

    @Test
    void testGetAllEmployees_whenOneEmployeesAdded_thenReturnListNotEmpty() {
        repository.addEmployee(mockEmployeeDTO);
        List<EmployeeDTO> list = repository.getAllEmployees();
        assertEquals(list.isEmpty(),false);
    }

    @Test
    void testGetAllEmployees_whenNoEmployeesInDatabase_thenReturnEmptyList() {
        List<EmployeeDTO> list = repository.getAllEmployees();
        assertEquals(list.isEmpty(),true);
    }

    @Test
    void testGetEmployee_whenOneEmployeeInDatabase_thenReturnEmployee() {
        repository.addEmployee(mockEmployeeDTO);
        assertDoesNotThrow(
                () -> repository.getEmployeeById(1)
        );
        EmployeeDTO employeeDTO = repository.getEmployeeById(1);
        assertNotNull(employeeDTO);
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
        repository.addEmployee(mockEmployeeDTO);
        assertThrows(
                DataAccessException.class,
                () -> repository.getEmployeeById(3)
        );
    }

    @Test
    void testRemoveEmployee_whenEmployeeInDatabaseExist_thenNoThrow() {
        repository.addEmployee(mockEmployeeDTO);
        assertDoesNotThrow(
                () -> repository.deleteEmployee(1)
        );
    }

    @Test
    void testRemoveEmployee_whenEmployeeInDatabaseNotExist_thenThrow() {
        repository.addEmployee(mockEmployeeDTO);
        repository.deleteEmployee(4);
    }
}