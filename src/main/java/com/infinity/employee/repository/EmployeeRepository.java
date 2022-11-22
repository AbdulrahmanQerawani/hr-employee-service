package com.infinity.employee.repository;

import com.infinity.employee.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    List<Employee> findAllByDepartmentId(Long departmentId);
    Employee findByEmployeeId(Long employeeId);
}
