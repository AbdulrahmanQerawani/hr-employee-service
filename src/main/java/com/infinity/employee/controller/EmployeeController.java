package com.infinity.employee.controller;

import com.infinity.employee.model.Employee;
import com.infinity.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/Employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> employee(@RequestParam Long employeeId) {
        Employee employee = service.getEmployee(employeeId);
        employee.add(linkTo(methodOn(EmployeeController.class).deleteEmployee(employee.getEmployeeId())).withRel("deleteEmployee"));
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@RequestParam Long employeeId){
        return ResponseEntity.ok(service.deleteEmployee(employeeId));
    }
}
