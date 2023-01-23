package com.infinity.employee.controller;

import com.infinity.employee.model.Employee;
import com.infinity.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /**
     * Returns the Product with the specified ID.
     *
     * @param employeeId    The ID of the Employee to retrieve.
     * @return              The Product with the specified ID.
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> employee(@PathVariable Long employeeId) {
        Employee employee = service.getEmployee(employeeId);
        employee.add(linkTo(methodOn(EmployeeController.class).deleteEmployee(employee.getEmployeeId())).withRel("deleteEmployee"));
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(service.deleteEmployee(employeeId));
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee request) {
        return ResponseEntity.ok(service.addEmployee(request));
    }
    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee request) {
        return ResponseEntity.ok(service.updateEmployee(request));
    }
}
