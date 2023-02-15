package com.infinity.employee;

import com.infinity.employee.model.Employee;
import com.infinity.employee.repository.EmployeeRepository;
import com.infinity.employee.service.EmployeeService;
import com.infinity.employee.utils.Gender;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceTest {
    /***
     * The service that we want to test
     */
    @Autowired
    EmployeeService employeeService;
    /***
     * A mock version of EmployeeRepository which will use in this test
     */
    @MockBean
    EmployeeRepository employeeRepository;

    @Nested
    @DisplayName("Get Employee Info Test")
    public class GetEmployeeTest {

        @Nested
        @DisplayName("Given That employee exists")
        public class Exists {

            Employee mockEmployee;

            @BeforeEach
            void setup() {
                mockEmployee = new Employee(1L,1L, "john", "Conner", Gender.MALE, "test@gmail.com","developer");
            }

            @Test
            @DisplayName("Then the employee record should return")
            void getEmployeeSuccessTest() {
                // Setup our mock
                doReturn(Optional.of(mockEmployee)).when(employeeRepository).findById(1L);

                // execute the service call
                Optional<Employee> optionalEmployee = employeeService.getEmployee(1L);

                // assert the response
                assertTrue(optionalEmployee.isPresent(), "Employee was not found");
                assertSame(mockEmployee, optionalEmployee.get(), "Employees should be the same");
            }
        }

        @Nested
        @DisplayName("Given That employee not exists")
        public class NotExists {

            Employee mockEmployee;

            @BeforeEach
            void setup() {
                mockEmployee = new Employee(1L,1L, "john", "Conner", Gender.MALE, "test@gmail.com","developer");
            }

            @Test
            @DisplayName("Then return empty")
            void getEmployeeNotFoundTest() {
                // Setup our mock
                doReturn(Optional.empty()).when(employeeRepository).findById(any());

                // execute the service call
                Optional<Employee> optionalEmployee = employeeService.getEmployee(1L);

                // assert the response
                assertFalse(optionalEmployee.isPresent(), "Employee was found, when it shouldn't be");
            }
        }

        @Nested
        @DisplayName("Given That many employees are exists")
        public class AllExists {

            List<Employee> mockEmployeeList = new ArrayList<>();

            @BeforeEach
            void setup() {
                Employee employee1 = new Employee(1L,1L, "john", "Conner", Gender.MALE, "john@gmail.com","developer");
                Employee employee2 = new Employee(2L,1L, "jack", "jack", Gender.FEMALE, "jack@gmail.com","developer");
                mockEmployeeList.add(employee1);
                mockEmployeeList.add(employee2);
            }

            @Test
            @DisplayName("Then return a list of employees")
            void getEmployeeListSuccessTest() throws TimeoutException {
                // Setup our mock
                doReturn(mockEmployeeList).when(employeeRepository).findAll();

                // execute the service call
                List<Employee> employeeList = employeeService.getAllEmployees();

                // assert the response
                assertFalse(employeeList.isEmpty(), "Employee list was empty, when shouldn't");
                assertSame(mockEmployeeList, employeeList, "Employees should be the same");
            }
        }


        @Nested
        @DisplayName("Given That no employees are exists")
        public class AllNotExists {

            List<Employee> mockEmployeeList;

            @BeforeEach
            void setup() {
                mockEmployeeList = new ArrayList<>();
            }

            @Test
            @DisplayName("Then return empty")
            void getEmployeeListNotFoundTest() throws TimeoutException {
                // Setup our mock
                doReturn(mockEmployeeList).when(employeeRepository).findAll();

                // execute the service call
                List<Employee> employeeList = employeeService.getAllEmployees();

                // assert the response
                assertTrue(employeeList.isEmpty(), "Employee list should be empty");
            }
        }

    }


    @Nested
    @DisplayName("Add New Employee Test")
    public class AddEmployeeTest {

        @Nested
        @DisplayName("Given the valid data")
        public class SuccessAdd {

            Employee mockEmployee;

            @BeforeEach
            void setup() {
                mockEmployee = new Employee(1L, 1L, "john", "Conner", Gender.MALE, "test@gmail.com","developer");
            }

            @Test
            @DisplayName("Then Employee successfully inserted and return added entity")
            void addEmployeeSuccess() {
                // Setup our mock
                doReturn(mockEmployee).when(employeeRepository).save(any());

                // execute the service call
                Employee returnEmployee = employeeService.addEmployee(mockEmployee);

                // assert the response
                assertNotNull(returnEmployee, "Product should be not null");
                assertEquals(1, returnEmployee.getVersion().intValue(),
                        "The version for a new Employee should be 1");
            }
        }

        @Nested
        @DisplayName("Given the InValid data")
        public class Failed {

            Employee mockEmployee;

            @BeforeEach
            void setup() {
                mockEmployee = new Employee();
            }

            @Test
            @DisplayName("Then Insert Failed")
            void AddEmployeeFailureTest() {

                // assert return
                assertNull(employeeService.addEmployee(null), " service must return null");

            }
        }

    }

}
