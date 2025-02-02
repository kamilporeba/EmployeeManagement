package pl.poreba.kamil.employeeTimeTracker.employees.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String email;
}