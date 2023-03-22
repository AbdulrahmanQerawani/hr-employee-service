package com.infinity.employee.controller;

import com.infinity.employee.client.RestTemplateClient;
import com.infinity.employee.model.Department;
import com.infinity.employee.model.Employee;
import com.infinity.employee.service.EmployeeService;
import com.infinity.employee.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//@RequestMapping("/api/v1/employee")
//@RequestMapping("/employee")
@RestController
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    // get LOGGER from SLF4J annotation
    private static final Logger LOGGER = log;
    private final EmployeeService employeeService;
    private final RestTemplateClient restTemplateClient;

    /**
     * Returns all employees in the database.
     *
     * @return Returns all employees in the database.
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<Employee>> getAllEmployees() throws TimeoutException {
        List<Employee> employeeList = employeeService.getAllEmployees();
        employeeList.forEach(employee -> {
            employee.add(linkTo(methodOn(EmployeeController.class)
                    .deleteEmployee(employee.getEmployeeId()))
                    .withRel("deleteEmployee")
                    .withSelfRel());
        });
        return ResponseEntity.ok(employeeList);
    }
    /**
     * Returns all employees with specified department.
     *
     * @param departmentId The ID of the department.
     *
     * @return Returns all employees with related department ID.
     */
    @GetMapping(value = "/department/{departmentId}")
    public ResponseEntity<List<Employee>> getAllEmployeesByDepartment(@PathVariable("departmentId") Long departmentId) {
        LOGGER.info("get all employees in the department with id:{}",departmentId);
        Department department = restTemplateClient.getDepartment(departmentId);
        List<Employee> employeeList = employeeService.getAllEmployeesByDepartmentId(departmentId);
        employeeList.forEach(employee -> {
            employee.setDepartmentName(department.getName());
            employee.add(linkTo(methodOn(EmployeeController.class)
                    .deleteEmployee(employee.getEmployeeId()))
                    .withRel("deleteEmployee")
                    .withSelfRel());
        });
        return ResponseEntity.ok(employeeList);
    }

    /**
     * Returns all employees with specified organization.
     *
     * @param organizationId The ID of the organization.
     *
     * @return {@return ResponseEntity} with all employees with related organization ID.
     */
    @GetMapping(value = "/organization/{organizationId}")
    public ResponseEntity<List<Employee>> getAllEmployeesByOrganization(@PathVariable("organizationId") Long organizationId) {
        LOGGER.info("get all employees with specified organization id:{}",organizationId);
        List<Employee> employeeList = employeeService.getAllEmployeesByOrganizationId(organizationId);
        employeeList.forEach(employee -> {
            employee.add(linkTo(methodOn(EmployeeController.class)
                    .deleteEmployee(employee.getEmployeeId()))
                    .withRel("deleteEmployee")
                    .withSelfRel());
        });
        return ResponseEntity.ok(employeeList);
    }

    /**
     * Returns all employees with specified organization.
     *
     * @param organizationId The ID of the organization.
     *
     * @return {@return   ArrayList}  with all employees with related organization ID.
     */
    @GetMapping(value = "/api/v2/organization/{organizationId}")
    public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId){
        List<Employee> employeeList = employeeService.getAllEmployeesByOrganizationId(organizationId);
        return employeeList;
    }

    /**
     * Returns Employee with the specified ID.
     *
     * @param employeeId The ID of the Employee to retrieve.
     * @return The Employee with the specified ID IF Exists.
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployee(@PathVariable Long employeeId) {
        LOGGER.info("get Employee by id={}", employeeId);
        return employeeService.getEmployee(employeeId)
                .map(emp -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(emp.getVersion()))
                                .location(new URI("/employee/" + emp.getEmployeeId()))
                                .body(emp.add(linkTo(methodOn(EmployeeController.class)
                                        .deleteEmployee(emp.getEmployeeId()))
                                        .withRel("deleteEmployee")));
                    } catch (URISyntaxException e) {
                        return ResponseEntity.internalServerError().build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new employee.
     *
     * @param request The Employee to create.
     * @return The Created employee.
     */
    @PostMapping(value = "/create")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee request,
                                                @RequestHeader(value = "Accept-Language", required = false)
                                                Locale locale) {

        LOGGER.info("Creating new employee with name: {} {}", request.getFirstName(), request.getLastName());
        // Creating the new employee
        Employee newEmployee = employeeService.addEmployee(request);

        // Build a created response
        try {
            return ResponseEntity
                    .created(new URI("/employee/" + newEmployee.getEmployeeId()))
                    .eTag(Integer.toString(newEmployee.getVersion()))
                    .body(newEmployee);
        } catch (URISyntaxException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Update Product with specified ID.
     *
     * @param employeeId the specified employee ID,
     * @param request    the Employee to update.
     * @param ifMatch    the request header value that's contains eTag (a version of resource).
     * @return A ResponseEntity with one of the following status:
     * 200 OK: if the Updated was successfully
     * 404 Not Found: if the employee with given id are not exists
     * 409 CONFLICT: if request header eTag value not equal current employee version value.
     * 500 Internal Server Error: if an error occur during updating
     */
    @PutMapping(value = "/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId,
                                            @RequestBody Employee request,
                                            @RequestHeader("If-Match") Integer ifMatch) {

        LOGGER.info("Updating employee with id: {}, name: {} {}", employeeId, request.getFirstName(), request.getLastName());

        // Get The existing employee.
        Optional<Employee> optionalEmployee = employeeService.getEmployee(employeeId);

        return optionalEmployee.map(employee -> {

            // Compare the eTag version
            LOGGER.info("Employee with ID: {} has a version of {}. Update is for If-Match: {}"
                    , employeeId, employee.getVersion(), ifMatch);

            if (!employee.getVersion().equals(ifMatch))
                return ResponseEntity.status(HttpStatus.CONFLICT).build();

            // Update Employee
            employee.setFirstName(request.getFirstName());
            employee.setLastName(request.getLastName());
            employee.setVersion(employee.getVersion() + 1);

            LOGGER.info("Updating product with id: {} -> name= {} {}, version={}"
                    , employee.getEmployeeId(), employee.getFirstName(),
                    employee.getLastName(), employee.getVersion());
            try {
                // Update the product and return an ok response
                if (employeeService.updateEmployee(employee)) {
                    return ResponseEntity
                            .ok()
                            .location(new URI("/employee/" + employee.getEmployeeId()))
                            .eTag(Integer.toString(employee.getVersion()))
                            .body(employee);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (URISyntaxException e) {
                return ResponseEntity.internalServerError().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes the employee with the specified ID.
     *
     * @param id The ID of the employee to delete.
     * @return A ResponseEntity with one of the following status:
     * 200 OK if the deleted was successfully
     * 404 Not Found if the employee with given id are not exists
     * 500 Internal Server Error if an error occur during deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        // Get the existing product
        Optional<Employee> optionalEmployee = employeeService.getEmployee(id);
        LOGGER.info("Deleting Employee with ID: {}", id);
        return optionalEmployee.map(employee -> {
            if (employeeService.deleteEmployee(employee.getEmployeeId())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }

}
