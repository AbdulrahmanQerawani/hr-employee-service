package com.infinity.employee;

import com.infinity.employee.model.Employee;
import com.infinity.employee.utils.Gender;

import org.joda.time.Months;
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
    @DisplayName("Within Constructor Valid Employee Creation")
    void testValidEmailAddressWithConstructor() {
        Employee employee = new Employee("Jone",
                "maxthon", Gender.MALE, "999123321", null,
                "test@g.com", null, null, null, null, null);
        assertNotNull(employee);
    }

    @Test
    @DisplayName("Within Constructor Employee Creation")
    void testInvalidEmailAddressWithConstructor() {
        assertThrows(RuntimeException.class, () -> {
            Employee employee = new Employee("Jone",
                    "maxthon", Gender.MALE, "999123321", null,
                    "test.com", null, null, null, null, null);
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
            employee.setIdentityCard("123321999");
            employee.setPhoneNumber("");
            employee.setEmailAddress("test@gmail.com");
            employee.setBirthDate(new Calendar.Builder()
                    .setDate(1990, 8 - 1, 22)
                    .build()
                    .getTime()
            );
            employee.setHireDate(new Calendar
                    .Builder()
                    .setFields(Calendar.YEAR, 2013, Calendar.MONTH, 9-1, Calendar.DAY_OF_MONTH, 01)
                    .build()
                    .getTime());
            employee.setUnitId(null);
            employee.setUnitName(null);
            employee.setUnitDescription(null);
            System.out.println(employee);
        });
    }

    @Test
    @DisplayName("Within Getter Methods")
    void employeeGetterTest() {
        Employee employee = new Employee("Jone",
                "maxthon", Gender.MALE, "999123321", null,
                "test@g.com", null, null, null, null, null);
        assertEquals("Employee(employeeId=null, firstName=Jone, lastName=maxthon, gender=MALE, identityCard=999123321, phoneNumber=null, emailAddress=test@g.com, birthDate=null, hireDate=null, unitId=null, unitName=null, unitDescription=null)", employee.toString());
    }
}
