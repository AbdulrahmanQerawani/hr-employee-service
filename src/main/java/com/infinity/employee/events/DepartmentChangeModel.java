package com.infinity.employee.events;

public record DepartmentChangeModel(String type, String action, Long departmentId, String correlationId) {
}
