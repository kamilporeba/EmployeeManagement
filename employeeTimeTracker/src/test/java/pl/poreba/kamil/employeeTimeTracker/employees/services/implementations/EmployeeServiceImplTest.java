package pl.poreba.kamil.employeeTimeTracker.employees.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;
import pl.poreba.kamil.employeeTimeTracker.employees.services.mapper.EmployeeMapper;
import pl.poreba.kamil.employeeTimeTracker.exceptions.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @Spy
    private EmployeeMapper mapper;

    @InjectMocks
    private EmployeeServiceImpl service;

    private EmployeeDTO mockedEmployeeDTO;
    private Employee mockedEmployee;
    private int id = 1;

    @BeforeEach
    void setUp() {
        mockedEmployeeDTO = EmployeeDTO.builder()
                .id(id)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@email.com")
                .position("Pracownik biurowy")
                .build();

        mockedEmployee = Employee.builder()
                .id(id)
                .firstName("JanDTO")
                .lastName("KowalskiDTO")
                .email("tesDTOt@email.com")
                .position("DTO")
                .build();
    }

    @Test
    void testAddEmployee_whenRequiredDataProvided_thenCreateEmployee() {
        lenient().when(repository.addEmployee(mockedEmployeeDTO)).thenReturn(mockedEmployeeDTO);

        assertDoesNotThrow(
                () -> service.addEmployee(mockedEmployee)
        );
    }

    @Test
    void testAddEmployee_whenNotAllRequiredDataProcess_thenThrow() {
        Employee employee = Employee.builder().id(1).build();
        doThrow(new ResourceNotFoundException("")).when(repository).addEmployee(any(EmployeeDTO.class));

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.addEmployee(employee)
        );
    }

    @Test
    void testGetEmployeeById_whenEmplyeeExistInDatabase_thenReturnEmployee() {
        when(repository.getEmployeeById(id)).thenReturn(mockedEmployeeDTO);

        Employee employee = service.getEmployeeById(id);

        assertEquals(employee.getEmail(), mockedEmployeeDTO.getEmail());
    }

    @Test
    void testGellAllEmployee_whenDatabaseNotEmpty_thenReturnList() {
        List<EmployeeDTO> employeeDTOS = Collections.singletonList(mockedEmployeeDTO);
        when(repository.getAllEmployees()).thenReturn(employeeDTOS);

        List<Employee> employeeList = service.getAllEmployees();

        assertEquals(employeeList.isEmpty(), false);
        assertEquals(employeeList.getFirst().getFirstName(), mockedEmployeeDTO.getFirstName());
    }

    @Test
    void testGetAllEmployee_whenDatabaseIsEmpty_thenReturnEmptyList() {
        when(repository.getAllEmployees()).thenReturn(Collections.emptyList());

        List<Employee> employeeList = service.getAllEmployees();

        assertEquals(employeeList.isEmpty(), true);
    }
}