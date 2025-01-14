package pl.poreba.kamil.employeeTimeTracker.employees.services;

import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    void addEmployee(EmployeeDTO employee);
    EmployeeDTO getEmployeeById(int id);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployee(int id);
}
