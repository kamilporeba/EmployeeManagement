package pl.poreba.kamil.employeeTimeTracker.employees.services.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;
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
    private ModelMapper mapper;

    @InjectMocks
    private EmployeeServiceImpl service;

    private Employee mockedEmployee;
    private EmployeeDTO mockedEmployeeDTO;
    private int id = 1;

    @BeforeEach
    void setUp() {
        mockedEmployee = Employee.builder()
                .id(id)
                .firstName("Jan")
                .lastName("Kowalski")
                .email("test@email.com")
                .position("Pracownik biurowy")
                .build();

        mockedEmployeeDTO = EmployeeDTO.builder()
                .id(id)
                .firstName("JanDTO")
                .lastName("KowalskiDTO")
                .email("tesDTOt@email.com")
                .position("DTO")
                .build();
    }

    @Test
    void testAddEmployee_whenRequiredDataProvided_thenCreateEmployee() {
        lenient().doNothing().when(repository).addEmployee(mockedEmployee);

        assertDoesNotThrow(
                () -> service.addEmployee(mockedEmployeeDTO)
        );
    }

    @Test
    void testAddEmployee_whenNotAllRequiredDataProcess_thenThrow() {
        EmployeeDTO employeeDTO = EmployeeDTO.builder().id(1).build();
        doThrow(new ResourceNotFoundException("")).when(repository).addEmployee(any(Employee.class));

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.addEmployee(employeeDTO)
        );
    }

    @Test
    void testGetEmployeeById_whenEmplyeeExistInDatabase_thenReturnEmployee() {
        when(repository.getEmployeeById(id)).thenReturn(mockedEmployee);

        EmployeeDTO employee = service.getEmployeeById(id);

        assertEquals(employee.getEmail(),mockedEmployee.getEmail());
    }

//    @Test
//    void testDeleteEmployee_whenEmployeeNotExistInDatabase_thenThrow() {
//        doThrow(new ResourceNotFoundException("")).when(repository).deleteEmployee(anyInt());
//
//        assertThrows(
//                ResourceNotFoundException.class,
//                () -> service.deleteEmployee(id)
//        );
//    }

    @Test
    void testGellAllEmployee_whenDatabaseNotEmpty_thenReturnList() {
        List<Employee> employees = Collections.singletonList(mockedEmployee);
        when(repository.getAllEmployees()).thenReturn(employees);

        List<EmployeeDTO> employeeDTOList = service.getAllEmployees();

        assertEquals(employeeDTOList.isEmpty(), false);
        assertEquals(employeeDTOList.getFirst().getFirstName(), mockedEmployee.getFirstName());
    }

    @Test
    void testGetAllEmployee_whenDatabaseIsEmpty_thenReturnEmptyList() {
        when(repository.getAllEmployees()).thenReturn(Collections.emptyList());

        List<EmployeeDTO> employeeDTOList = service.getAllEmployees();

        assertEquals(employeeDTOList.isEmpty(), true);
    }
}