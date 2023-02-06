package com.infinity.employee.service;

import com.infinity.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


public interface EmployeeService {

    /**
     * Returns the employee with specified ID.
     *
     * @param employeeId The ID of the employee to be retrieved.
     * @return The requested employee if exists.
     */
    Optional<Employee> getEmployee(Long employeeId);

    /**
     * Returns all employees in the database.
     *
     * @return All employees in the database.
     */
    List<Employee> getAllEmployee();

    /**
     * Saves the specified employee to the database.
     *
     * @param employee The employee to save to the database.
     * @return The saved employee.
     */
    Employee addEmployee(Employee employee);

    /**
     * Updates the specified employee, identified with its ID.
     *
     * @param employee The employee to update
     * @return True if the update succeeded, otherwise false.
     */
    Boolean updateEmployee(Employee employee);

    /**
     * Deletes the specified employee, identified with its ID.
     *
     * @param employeeId The id of the employee to delete
     * @return True if the Operation was successful, otherwise false.
     */
    Boolean deleteEmployee(Long employeeId);


}
