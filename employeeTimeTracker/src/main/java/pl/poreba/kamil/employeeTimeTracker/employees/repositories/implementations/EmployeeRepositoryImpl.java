package pl.poreba.kamil.employeeTimeTracker.employees.repositories.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.poreba.kamil.employeeTimeTracker.employees.entities.Employee;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;


import java.util.List;

@Component
@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    RowMapper<Employee> rowMapper = ((rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setPosition(rs.getString("position"));
        employee.setEmail(rs.getString("email"));
        return  employee;
    });

    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addEmployee(Employee employee) throws DataAccessException  {
        String sql = "INSERT INTO employees (first_name, last_name, position, email) VALUES (?,?,?,?)";
        try {
            jdbcTemplate.update(sql,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getPosition(),
                    employee.getEmail());
            log.info("New employee added: " + employee.getFirstName() + employee.getLastName());
        } catch (DataAccessException error) {
            log.error("Problem with added employee " + error.getLocalizedMessage());
            throw error;
        }
    }


    @Override
    public Employee getEmployeeById(int id) throws DataAccessException {
        String sql = "select * from employees where id = ?";
        try {
            Employee employee = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            log.info("Employee found id: " + employee.getId());
            return employee;
        } catch (DataAccessException error) {
            log.error(String.format("Problem with retrieve user with id %s \n %s ", id, error.getLocalizedMessage()));
            throw error;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = "select * from employees";
        List<Employee> employees = jdbcTemplate.query(sql,rowMapper);
        log.info("Retrieve employees");
        return employees;
    }

    @Override
    public void deleteEmployee(int id) throws DataAccessException {
        String sql = "delete from employees where id = ?";
        try {
            jdbcTemplate.update(sql,id);
            log.info(String.format("Employee %d removed", id));
        } catch (DataAccessException error) {
            log.error(String.format("Problem with removing user with id %s \n %s ", id, error.getLocalizedMessage()));
            throw error;
        }
    }
}
