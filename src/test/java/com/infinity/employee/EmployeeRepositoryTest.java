package com.infinity.employee;


import com.infinity.employee.utils.Gender;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import com.infinity.employee.model.Employee;
import com.infinity.employee.repository.EmployeeRepository;

import java.util.List;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    private final EmployeeRepository employeeRepository;

    public EmployeeRepositoryTest(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Test
    @Order(1)
    void testAddEmployee() {
        Employee employee = new Employee(1L,1L, "john", "Conner", Gender.MALE, "test@gmail.com","developer");
        employee = employeeRepository.save(employee);
        Assert.notNull(employee, "Employee is null.");
        Assert.isTrue(employee.getEmployeeId() == 1L, "Employee bad id.");
    }

    @Test
    @Order(2)
    void testFindAll() {
        List<Employee> employees = employeeRepository.findAll();
        Assert.isTrue(employees.size() == 1, "Employees size is wrong.");
        Assert.isTrue(employees.get(0).getEmployeeId() == 1L, "Employee bad id.");
    }

    @Test
    @Order(3)
    void testFindByDepartment() {
        List<Employee> employees = employeeRepository.findEmployeesByDepartmentId(1L);
        Assert.isTrue(employees.size() == 1, "Employees size is wrong.");
        Assert.isTrue(employees.get(0).getEmployeeId() == 1L, "Employee bad id.");
    }

    @Test
    @Order(4)
    void testFindByOrganization() {
        List<Employee> employees = employeeRepository.findEmployeesByOrganizationId(1L);
        Assert.isTrue(employees.size() == 1, "Employees size is wrong.");
        Assert.isTrue(employees.get(0).getEmployeeId() == 1L, "Employee bad id.");
    }

    @Test
    @Order(5)
    void testFindById() {
        Employee employee = employeeRepository.findById(1L).get();
        Assert.notNull(employee, "Employee not found.");
        Assert.isTrue(employee.getEmployeeId() == 1L, "Employee bad id.");
    }

}
