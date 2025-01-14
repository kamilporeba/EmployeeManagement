package pl.poreba.kamil.employeeTimeTracker.employees.entities;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String email;
}