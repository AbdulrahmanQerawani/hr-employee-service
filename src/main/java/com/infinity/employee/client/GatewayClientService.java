package com.infinity.employee.client;

import com.infinity.employee.model.Department;
import com.infinity.employee.model.Organization;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class GatewayClientService {

    private static final Logger LOGGER = log;
    private static final String AUTHORIZATION = "Authorization";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${infinity.gateway.baseUrl}")
    private String baseUrl = "";

    /**
     * find department with specified Department ID
     *
     * @param departmentId specified ID
     * @return Department
     * @apiNote this method sends down stream call to department-service throw gateway server
     */
    public Department findDepartmentById(@NotNull Long departmentId) {
        Department department = doRestExchange(baseUrl + "/department/{departmentId}", departmentId,Department.class);
        return department;
    }

    /**
     * find organization with specified Organization ID
     *
     * @param organizationId specified ID
     * @return Department
     * @apiNote this method sends down stream call to department-service throw gateway server
     */
    public Organization findOrganizationById(@NotNull Long organizationId) {
        Organization organization = doRestExchange(baseUrl + "/organization/organization/{organizationId}", organizationId,Organization.class);
        return organization;
    }

    private <T> T doRestExchange(@NotNull String clientUrl, Long params,Class<T> type) {
        /*** get Authentication token */
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, "Bearer " + jwt.getTokenValue());
        LOGGER.debug("down stream call url:{}", clientUrl);
        try {
            return restTemplate.exchange(clientUrl, HttpMethod.GET, new HttpEntity<>(headers),type, params).getBody();
        } catch (ResourceAccessException e) {
            LOGGER.error("Failed to access resource: GateWay Client Service. error{}", e);
            return null;
        }
    }
}
