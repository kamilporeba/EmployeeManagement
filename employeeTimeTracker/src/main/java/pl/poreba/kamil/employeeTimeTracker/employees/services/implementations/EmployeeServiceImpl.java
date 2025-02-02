package pl.poreba.kamil.employeeTimeTracker.employees.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;
import pl.poreba.kamil.employeeTimeTracker.employees.services.EmployeeService;
import pl.poreba.kamil.employeeTimeTracker.employees.services.mapper.EmployeeMapper;
import pl.poreba.kamil.employeeTimeTracker.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper employeeMapper;

    @Override
    public Employee addEmployee(Employee employee) {
        EmployeeDTO employeeDTOEntity =  employeeMapper.map(employee);
        repository.addEmployee(employeeDTOEntity);
        return employeeMapper.map(employeeDTOEntity);
    }

    @Override
    public Employee getEmployeeById(int id) {
        try {
            EmployeeDTO employeeDTOEntity = getEmployeeDTOIfExists(id);
            return employeeMapper.map(employeeDTOEntity);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository
                .getAllEmployees()
                .stream()
                .map(
                        employeeEntity -> employeeMapper.map(employeeEntity)
                )
                .collect(Collectors.toList()
                );
    }

    @Override
    public void deleteEmployee(int id) {
        try {
            getEmployeeById(id);
            repository.deleteEmployee(id);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private EmployeeDTO getEmployeeDTOIfExists(int id) throws ResourceNotFoundException {
        try {
            return repository.getEmployeeById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("EmployeeDTO not found");
        }
    }
}
