package com.infinity.employee.service;

import com.infinity.employee.model.Employee;
import com.infinity.employee.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository repository;
    @Autowired
    MessageSource messages;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(EmployeeRepository repository, MessageSource messages) {
        this.repository = repository;
        this.messages = messages;
    }

    public Employee getEmployee(Long employeeId) {
        Employee employee = repository.findByEmployeeId(employeeId);
        if (employee.equals(null)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("employee.search.error.message", null, null), employeeId));
        }
        return employee;
    }

    public List<Employee> getDeptEmployeesList(Long departmentId) {
        List<Employee> employees = repository.findAllByDepartmentId(departmentId);
        if (employees.equals(null)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("employee.search.error.message", null, null), departmentId));
        }
        return employees;
    }

    public String deleteEmployee(Long employeeId) {
        Employee employee = repository.findByEmployeeId(employeeId);
        if (employee.equals(null)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("employee.delete.error.message", null, null), employeeId));
        }
        logger.debug("Employee with id: %s and his name is \"%s\" has been Deleted", employee.getLastName(),employeeId);
        repository.delete(employee);
        return String.format(messages.getMessage("employee.delete.success.message",
                null, null), employeeId);


    }

}
