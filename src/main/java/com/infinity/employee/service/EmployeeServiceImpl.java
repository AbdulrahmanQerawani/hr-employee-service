package com.infinity.employee.service;

import com.infinity.employee.model.Employee;
import com.infinity.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository repository;
    private final MessageSource messages;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, MessageSource messages) {
        this.repository = repository;
        this.messages = messages;
    }

    @Override
    public Optional<Employee> getEmployee(Long employeeId) {
        Optional<Employee> employee = repository.findById(employeeId);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return repository.findAll();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (Objects.nonNull(employee)) {
            employee.setVersion(1);
            return repository.save(employee);
        }
        return null;
    }

    @Override
    public Boolean updateEmployee(Employee employee) {
        try {
            repository.save(employee);
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
            repository.deleteById(employeeId);
            LOGGER.info("employee with id: %s has been deleted.", employeeId);
            return Boolean.TRUE;
        } catch (Exception exception) {
            LOGGER.error("Error Occur in deleteEmployee Service -> ", exception);
            return Boolean.FALSE;
        }
    }
}
