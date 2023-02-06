package com.infinity.employee;

import com.infinity.employee.model.Employee;
import com.infinity.employee.utils.Gender;

import org.joda.time.Months;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpHeaders;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static org.checkerframework.checker.units.UnitsTools.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
    @Test
    @DisplayName("Within Employee Creation")
    void createEmployeeTest() {
        Employee employee = new Employee();
        assertNotNull(employee);
    }

    @Test
    @Disabled
    @DisplayName("Within Constructor Valid Employee Creation")
    void testValidEmailAddressWithConstructor() {
        Employee employee = new Employee("Jone",
                "maxthon", Gender.MALE,
                "test.com");
        assertNotNull(employee);
    }

    @Test
    @DisplayName("Within Constructor Employee Creation")
    void testInvalidEmailAddressWithConstructor() {
        assertThrows(RuntimeException.class, () -> {
            Employee employee = new Employee("Jone",
                    "maxthon", Gender.MALE,
                    "test.com");
        });
    }

    @Test
    @DisplayName("Within Constructor Throws")
    void employeeSetterTest() {
        assertDoesNotThrow(() -> {
            Employee employee = new Employee();
            employee.setEmployeeId(1L);
            employee.setFirstName("Jone");
            employee.setLastName("maxthon");
            employee.setGender(Gender.MALE);
            employee.setEmailAddress("test@gmail.com");
        });
    }

    @Test
    @DisplayName("Within Getter Methods")
    void employeeGetterTest() {
        Employee employee = new Employee(1L,"Jone",
                "maxthon", Gender.MALE,
                "test@g.com");
        assertEquals("Employee(employeeId=1, firstName=Jone, lastName=maxthon, gender=MALE, emailAddress=test@g.com, unitName=null, unitDescription=null, version=null)", employee.toString());
    }
}
