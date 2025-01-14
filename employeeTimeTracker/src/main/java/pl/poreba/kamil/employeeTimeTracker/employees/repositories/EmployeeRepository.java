package pl.poreba.kamil.employeeTimeTracker.employees.repositories;

import org.springframework.dao.DataAccessException;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;

import java.util.List;

public interface EmployeeRepository {
    void addEmployee(Employee employee) throws DataAccessException;
    Employee getEmployeeById(int id) throws DataAccessException;
    List<Employee> getAllEmployees();
    void deleteEmployee(int id) throws DataAccessException;
}
