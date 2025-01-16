package pl.poreba.kamil.employeeTimeTracker.employees.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.poreba.kamil.employeeTimeTracker.aop.TimeDuration;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.services.EmployeeService;
import pl.poreba.kamil.employeeTimeTracker.exceptions.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @TimeDuration
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        try {
            List<EmployeeDTO> employeeList = employeeService.getAllEmployees();
            return ResponseEntity.ok(employeeList);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    @PostMapping
    @TimeDuration
    public ResponseEntity addNewEmployee(@RequestBody @Valid EmployeeDTO employee) {
        try {
            employeeService.addEmployee(employee);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/{employeeId}")
    @TimeDuration
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "employeeId") int id) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employeeDTO);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }

    @DeleteMapping
    @TimeDuration
    public ResponseEntity removeEmployee(@RequestParam(name = "employeeId") int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }
}
