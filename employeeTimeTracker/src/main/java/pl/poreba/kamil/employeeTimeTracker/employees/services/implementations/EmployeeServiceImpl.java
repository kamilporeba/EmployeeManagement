package pl.poreba.kamil.employeeTimeTracker.employees.services.implementations;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;
import pl.poreba.kamil.employeeTimeTracker.employees.services.EmployeeService;
import pl.poreba.kamil.employeeTimeTracker.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper mapper;

    @Override
    public void addEmployee(EmployeeDTO employee) {
        Employee employeeEntity = mapper.map(employee, Employee.class);
        repository.addEmployee(employeeEntity);
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        try {
            Employee employeeEntity = getEmployeeDTOIfExists(id);
            return mapper.map(employeeEntity, EmployeeDTO.class);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return repository
                .getAllEmployees()
                .stream()
                .map(
                        employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class)
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

    private Employee getEmployeeDTOIfExists(int id) throws ResourceNotFoundException {
        try {
            return repository.getEmployeeById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Employee not found");
        }
    }
}
