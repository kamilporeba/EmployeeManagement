package pl.poreba.kamil.employeeTimeTracker.employees.repositories.implementations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.poreba.kamil.employeeTimeTracker.employees.dtos.EmployeeDTO;
import pl.poreba.kamil.employeeTimeTracker.employees.repositories.EmployeeRepository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private JdbcTemplate jdbcTemplate;

    RowMapper<EmployeeDTO> rowMapper = ((rs, rowNum) -> {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(rs.getInt("id"));
        employeeDTO.setFirstName(rs.getString("first_name"));
        employeeDTO.setLastName(rs.getString("last_name"));
        employeeDTO.setPosition(rs.getString("position"));
        employeeDTO.setEmail(rs.getString("email"));
        return employeeDTO;
    });

    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) throws DataAccessException  {
        String sql = "INSERT INTO employees (first_name, last_name, position, email) VALUES (?,?,?,?)";

        KeyHolder key = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    final PreparedStatement ps = connection.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, employeeDTO.getFirstName());
                    ps.setString(2, employeeDTO.getLastName());
                    ps.setString(3, employeeDTO.getPosition());
                    ps.setString(4, employeeDTO.getEmail());
                    return ps;
                }
            }, key);

            log.info("New employeeDTO added: " + employeeDTO.getFirstName() + employeeDTO.getLastName());
            employeeDTO.setId((int) key.getKeys().get("id"));
            return employeeDTO;
        } catch (DataAccessException error) {
            log.error("Problem with added employee " + error.getLocalizedMessage());
            throw error;
        }
    }


    @Override
    public EmployeeDTO getEmployeeById(int id) throws DataAccessException {
        String sql = "select * from employees where id = ?";
        try {
            EmployeeDTO employeeDTO = jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
            log.info("EmployeeDTO found id: " + employeeDTO.getId());
            return employeeDTO;
        } catch (DataAccessException error) {
            log.error(String.format("Problem with retrieve user with id %s \n %s ", id, error.getLocalizedMessage()));
            throw error;
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        String sql = "select * from employees";
        List<EmployeeDTO> employeeDTOS = jdbcTemplate.query(sql,rowMapper);
        log.info("Retrieve employee");
        return employeeDTOS;
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
