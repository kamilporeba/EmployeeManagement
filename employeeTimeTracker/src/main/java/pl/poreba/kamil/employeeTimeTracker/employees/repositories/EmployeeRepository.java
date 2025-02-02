package pl.poreba.kamil.employeeTimeTracker.employees.repositories;

import org.springframework.dao.DataAccessException;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeRepository {
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws DataAccessException;
    EmployeeDTO getEmployeeById(int id) throws DataAccessException;
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployee(int id) throws DataAccessException;
}
