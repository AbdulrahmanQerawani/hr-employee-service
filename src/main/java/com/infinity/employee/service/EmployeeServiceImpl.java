package com.infinity.employee.service;

import com.infinity.employee.model.Employee;
import com.infinity.employee.repository.EmployeeRepository;
import com.infinity.employee.utils.Gender;
import com.infinity.employee.utils.UserContextHolder;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final MessageSource messages;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, MessageSource messages) {
        this.employeeRepository = employeeRepository;
        this.messages = messages;
    }

    @Override
    public Optional<Employee> getEmployee(Long employeeId) {
        LOGGER.info("getEmployee service called with {} args", employeeId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            LOGGER.info("employee with {} is found", employeeId);
        }
        return employee;
    }

    @Override
    @CircuitBreaker(name = "employeeservice",fallbackMethod = "fallBack")
    @Bulkhead(name = "bulkheadEmployeeservice",fallbackMethod = "fallBackBulkhead")
    @Retry(name = "retryEmployeeservice",fallbackMethod = "fallBack")
    @RateLimiter(name = "employeeservice",fallbackMethod = "fallBack")
    public List<Employee> getAllEmployees() {
        LOGGER.debug("EmployeeServiceImpl:getAllEmployee Correlation id: {}",
                UserContextHolder.getContext().getCorrelationId());
        randomlyRunLong();
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getAllEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findEmployeesByDepartmentId(departmentId);
    }

    /**
     * Returns all employees with specified organization ID.
     *
     * @param organizationId The ID of the organization
     * @return All employees with related organization ID.
     */
    @Override
    public List<Employee> getAllEmployeesByOrganizationId(Long organizationId) {
        return employeeRepository.findEmployeesByOrganizationId(organizationId);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (Objects.nonNull(employee)) {
            employee.setVersion(1);
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public Boolean updateEmployee(Employee employee) {
        try {
            employeeRepository.save(employee);
            LOGGER.info("employee with id: %s successfully Updated.", employee.getEmployeeId());
            return Boolean.TRUE;
        } catch (Exception exception) {
            LOGGER.error("Error Occur in updateEmployee Service -> ", exception);
            return Boolean.FALSE;
        }

    }

    @Override
    public Boolean deleteEmployee(Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
            LOGGER.info("employee with id: %s has been deleted.", employeeId);
            return Boolean.TRUE;
        } catch (Exception exception) {
            LOGGER.error("Error Occur in deleteEmployee Service -> ", exception);
            return Boolean.FALSE;
        }
    }
    private List<Employee> fallBack(Throwable throwable){
        List<Employee> employeeList = List.of(new Employee(1L,1L, "fallbackMethod sucker", "Conner", Gender.MALE, "test@gmail.com","developer"));
        LOGGER.debug(throwable.getMessage());
        return employeeList;
    }
    private List<Employee> fallBackBulkhead(Throwable throwable){
        List<Employee> employeeList = List.of(new Employee(1L,1L, "bulkhead fuck", "Conner", Gender.MALE, "test@gmail.com","developer"));
        LOGGER.debug(throwable.getMessage());
        return employeeList;
    }

    private void randomlyRunLong(){
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum==1) {sleep();}
    }
    private void sleep(){
        try {
            Thread.sleep(5000);
            throw new java.util.concurrent.TimeoutException();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
