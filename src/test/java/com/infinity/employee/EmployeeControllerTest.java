package com.infinity.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinity.employee.model.Employee;
import com.infinity.employee.service.EmployeeService;
import com.infinity.employee.utils.Gender;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    private static String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    @DisplayName("GET /employee/1 - Found")
    void getEmployeeByIdTest() throws Exception {
        // Set up our mocked employeeService
        Employee mockEmployee = new Employee(1L, "john", "Conner", Gender.MALE, "test@gmail.com");
        mockEmployee.setVersion(1);
        doReturn(Optional.of(mockEmployee)).when(employeeService).getEmployee(1L);

        // Execute The GET request
        mockMvc.perform(get("/employee/{employeeId}", 1))

                // Validate the response code and content
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                // Validate the header
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/employee/1"))
                // Validate the returned fields
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.firstName", is("john")))
                .andExpect(jsonPath("$.lastName", is("Conner")))
                .andExpect(jsonPath("$.gender", is(Gender.MALE.toString())))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @Order(2)
    @DisplayName("GET /employee/1 - NotFound")
    void getProductByIdNotFoundTest() throws Exception {
        // Set up our mocked employeeService
        doReturn(Optional.empty()).when(employeeService).getEmployee(1L);

        // Execute the GET request
        mockMvc.perform(get("/employee/{employeeId}", 1))

                // Validate that we get a 404 Not Found response code
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(3)
    @DisplayName("POST /employee - Success")
    void postProductSuccessTest() throws Exception {
        // Set up our mocked employeeService
        Employee postEmployee = new Employee("john", "Conner", Gender.MALE, "test@gmail.com");
        Employee mockEmployee = new Employee(1L, "john", "Conner", Gender.MALE, "test@gmail.com");
        mockEmployee.setVersion(1);
        doReturn(mockEmployee).when(employeeService).addEmployee(any());

        // Execute The POST request
        mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postEmployee)))

                // Validate the response code and content
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/hal+json"))

                // Validate the header
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/employee/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.firstName", is("john")))
                .andExpect(jsonPath("$.lastName", is("Conner")))
                .andExpect(jsonPath("$.gender", is(Gender.MALE.toString())))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @Order(4)
    @DisplayName("PUT /employee/{employeeId} - Success")
    void employeePutSuccessTest() throws Exception {
        // Set up our mocked employeeService
        Employee putEmployee = new Employee("john", "Conner", Gender.MALE, "test@gmail.com");
        Employee mockEmployee = new Employee(1L, "john", "Conner", Gender.MALE, "test@gmail.com");
        mockEmployee.setVersion(1);
        doReturn(Optional.of(mockEmployee)).when(employeeService).getEmployee(1L);
        doReturn(Boolean.TRUE).when(employeeService).updateEmployee(any());

        // Execute The PUT request
        mockMvc.perform(put("/employee/{employeeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.IF_MATCH, 1)
                        .content(asJsonString(putEmployee)))

                // Validate the response code and content
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))

                // Validate the header
                .andExpect(header().string(HttpHeaders.ETAG, "\"2\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/employee/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.firstName", is("john")))
                .andExpect(jsonPath("$.lastName", is("Conner")))
                .andExpect(jsonPath("$.gender", is(Gender.MALE.toString())))
                .andExpect(jsonPath("$.version", is(2)));

    }

    @Test
    @Order(5)
    @DisplayName("PUT /employee/{id} - version Mismatch")
    void employeePutVersionMismatchTest() throws Exception {
        // Set up our mocked employeeService
        Employee putEmployee = new Employee("john", "Conner", Gender.MALE, "test@gmail.com");
        Employee mockEmployee = new Employee(1L, "john", "Conner", Gender.MALE, "test@gmail.com");
        mockEmployee.setVersion(2);
        doReturn(Optional.of(mockEmployee)).when(employeeService).getEmployee(1L);
        doReturn(Boolean.TRUE).when(employeeService).updateEmployee(any());
        // Execute The PUT request
        mockMvc.perform(put("/employee/{employeeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.IF_MATCH, 1)
                        .content(asJsonString(putEmployee)))

                // Validate the response code and content
                .andExpect(status().isConflict());
    }

    @Test
    @Order(6)
    @DisplayName("PUT /employee/{id} - Not Found")
    void employeePutNotFoundTest() throws Exception {

        // Set up our mocked employeeService
        Employee putEmployee = new Employee("john", "Conner", Gender.MALE, "test@gmail.com");
        doReturn(Optional.empty()).when(employeeService).getEmployee(1L);

        // Execute The PUT request
        mockMvc.perform(put("/employee/{employeeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.IF_MATCH, 1)
                        .content(asJsonString(putEmployee)))

                // Validate the response code and content
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(7)
    @DisplayName("DELETE /employee/{employeeId} - Success")
    void employeeDeleteSuccessTest() throws Exception {
        // Set up our mocked employeeService
        Employee mockEmployee = new Employee(1L, "john", "Conner", Gender.MALE, "test@gmail.com");
        mockEmployee.setVersion(1);
        doReturn(Optional.of(mockEmployee)).when(employeeService).getEmployee(1L);
        doReturn(Boolean.TRUE).when(employeeService).deleteEmployee(any());

        // Execute the DELETE request to Delete employee with id: 1
        mockMvc.perform(delete("/employee/{employeeId}", 1))
                .andExpect(status().isOk());

    }

    @Test
    @Order(8)
    @DisplayName("DELETE /employee/{employeeId} - Not Found")
    void employeeDeleteNotFoundTest() throws Exception {
        // Set up our mocked employeeService
        doReturn(Optional.empty()).when(employeeService).getEmployee(1L);

        // Execute the DELETE request
        mockMvc.perform(delete("/employee/{employeeId}", 1))
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE /employee/{employeeId} - Failure")
    void employeeDeleteFailureTest() throws Exception {
        // Set up our mocked employeeService
        Employee mockEmployee = new Employee(1L, "john", "Conner", Gender.MALE, "test@gmail.com");
        mockEmployee.setVersion(1);
        doReturn(Optional.of(mockEmployee)).when(employeeService).getEmployee(1L);
        doReturn(Boolean.FALSE).when(employeeService).deleteEmployee(1L);

        // Execute the DELETE request to Delete product with id: 1
        mockMvc.perform(delete("/employee/{employeeId}", 1))
                .andExpect(status().isInternalServerError());
    }
}
