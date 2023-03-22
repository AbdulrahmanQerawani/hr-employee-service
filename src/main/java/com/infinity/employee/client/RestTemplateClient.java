package com.infinity.employee.client;

import com.infinity.employee.model.Department;
import com.infinity.employee.model.Organization;
import com.infinity.employee.repository.DepartmentRedisRepository;
import com.infinity.employee.repository.OrganizationRedisRepository;
import com.infinity.employee.utils.UserContextHolder;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Observed(name = "read Data from Redis")
public class RestTemplateClient {
    private static final Logger LOGGER = log;
    private final GatewayClientService gatewayClientService;
    private final DepartmentRedisRepository departmentRedisRepository;
    private final OrganizationRedisRepository organizationRedisRepository;

    private <T> T checkRedisCache(Class<T> type, Long id) {
        try {
            assert type != null : "Class Type cannot be null";
            if (type.getClass().equals(Department.class.getClass()))
                return (T) departmentRedisRepository.findById(id).orElse(null);
            else if (type.equals(Organization.class.getClass()))
                return (T) organizationRedisRepository.findById(id).orElse(null);
            else return null;
        } catch (Exception ex) {
            LOGGER.error("Error encountered while trying to retrieve object{} with id{} check Redis Cache. Exception {}", type.getClass(), id, ex);
            return null;
        }
    }

    private void cacheDepartmentObject(Department department) {
        try {
            departmentRedisRepository.save(department);
        } catch (Exception ex) {
            LOGGER.error("Unable to cache department {} in Redis. Exception {}", department.getId(), ex);
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            organizationRedisRepository.save(organization);
        } catch (Exception ex) {
            LOGGER.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex);
        }
    }

    public Department getDepartment(Long departmentId) {
        LOGGER.debug("In Employee Service.getDepartment: {}", UserContextHolder.getContext().getCorrelationId());
        Department department = checkRedisCache(Department.class, departmentId);
        if (department != null) {
            LOGGER.debug("I Have successfully retrieved an department {} from the redis cache: {}", departmentId, department);
            return department;
        }
        LOGGER.debug("Unable to locate department from the redis cache: {}.", departmentId);
        department = gatewayClientService.findDepartmentById(departmentId);
        if (department != null) {
            cacheDepartmentObject(department);
        }
        return department;
    }

    public Organization getOrganization(Long organizationId) {
        LOGGER.debug("In Employee Service.getOrganization: {}", UserContextHolder.getContext().getCorrelationId());
        Organization organization = checkRedisCache(Organization.class, organizationId);
        if (organization != null) {
            LOGGER.debug("I Have successfully retrieved an organization {} from the redis cache: {}", organizationId, organization);
            return organization;
        }
        LOGGER.debug("Unable to locate organization from the redis cache: {}.", organizationId);
        organization = gatewayClientService.findOrganizationById(organizationId);
        if (organization != null) {
            cacheOrganizationObject(organization);
        }
        return organization;
    }
}
