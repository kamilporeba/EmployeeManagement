package pl.poreba.kamil.employeeTimeTracker.employees.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Integer id;
    @NotBlank(message = "First name can not be blank")
    private String firstName;
    @NotBlank(message = "Last name can not be blank")
    private String lastName;

    private String position;

    @Email
    private String email;
}
