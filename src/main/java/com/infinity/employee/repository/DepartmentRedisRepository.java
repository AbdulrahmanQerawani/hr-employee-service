package com.infinity.employee.repository;

import com.infinity.employee.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRedisRepository extends CrudRepository<Department,Long> {
}
