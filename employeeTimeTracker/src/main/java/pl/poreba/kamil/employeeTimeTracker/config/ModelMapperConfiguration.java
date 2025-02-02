package pl.poreba.kamil.employeeTimeTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.poreba.kamil.employeeTimeTracker.employees.services.mapper.EmployeeMapper;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public EmployeeMapper getModelMapper() {
        return new EmployeeMapper();
    }
}
