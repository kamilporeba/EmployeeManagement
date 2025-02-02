package pl.poreba.kamil.employeeTimeTracker.employees.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.poreba.kamil.employeeTimeTracker.aop.TimeDuration;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @TimeDuration
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping
    @TimeDuration
    public ResponseEntity addNewEmployee(@RequestBody @Valid Employee employee) {
        Employee addedEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<Employee>(addedEmployee, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{employeeId}")
    @TimeDuration
    public ResponseEntity<Employee> getEmployee(@PathVariable(name = "employeeId") int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping(path = "/{employeeId}")
    @TimeDuration
    public ResponseEntity removeEmployee(@PathVariable(name = "employeeId") int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
