package pl.poreba.kamil.employeeTimeTracker.employees.services;

import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee getEmployeeById(int id);
    List<Employee> getAllEmployees();
    void deleteEmployee(int id);
}
