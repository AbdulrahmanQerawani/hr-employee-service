package com.infinity.employee;

import com.infinity.employee.model.Unit;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Testing infinity.Unit")
public class UnitTest {
    Unit unit;
    @BeforeEach
    void setup(){
        unit = new Unit();
        System.out.println("Unit has been Created");
    }
    @Nested
    @DisplayName("when Add New Unit")
    class addNewUnit{
        @DisplayName("add with Constructor")
        @Test
        @Order(1)
        void withConstructor(){
            Unit unit = new Unit(1L,"HR","Human Resources Unit");
            assertNotNull(unit);
        }

        @DisplayName("add Without Constructor")
        @Test
        @Order(2)
        void withoutConstructor(){
            Unit unit = new Unit();
            assertNotNull(unit);
        }
    }
    @Nested
    @DisplayName("when set invalid name")
    class invalidUnitId{
        Unit unit;

        @BeforeEach
        void setup(){
            unit = new Unit();
            System.out.println("Unit has been Created");
        }
        @Test
        @DisplayName("Then Throw Runtime Exception")
        void invalidUnitName(){
            assertThrows(RuntimeException.class, ()->{
                unit.setUnitName("");
            });
        }
    }

    @Test
    @DisplayName("when set valid name")
    void validUnitName(){
        assertDoesNotThrow(()->{
            unit.setUnitName("HR");
        });
    }



}
