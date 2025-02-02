package pl.poreba.kamil.employeeTimeTracker.employees.services.mapper;

import org.springframework.stereotype.Component;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;

@Component
public class EmployeeMapper {
    public Employee map(EmployeeDTO baseObject) {
        return Employee.builder()
                .id(baseObject.getId())
                .firstName(baseObject.getFirstName())
                .lastName(baseObject.getLastName())
                .position(baseObject.getPosition())
                .email(baseObject.getEmail())
                .build();
    }

    public EmployeeDTO map(Employee baseObject) {
        return EmployeeDTO.builder()
                .firstName(baseObject.getFirstName())
                .lastName(baseObject.getLastName())
                .position(baseObject.getPosition())
                .email(baseObject.getEmail())
                .build();
    }
}
