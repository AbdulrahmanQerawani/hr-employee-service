package com.infinity.employee.utils;

import java.io.IOException;


import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static io.opentelemetry.context.Context.current;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserContextFilter implements Filter {
    private static final Logger LOGGER = log;
    public String getOTelTraceId() {
        // Get the current span from the open telemetry context
        Context context = current();
        Span currentSpan = Span.fromContext(context);
        // Get traceId from open telemetry Current Span
        return  currentSpan.getSpanContext().getTraceId();
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserContext userContextHolder = UserContextHolder.getContext();
        userContextHolder.setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        userContextHolder.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        userContextHolder.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        userContextHolder.setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));
        userContextHolder.setDepartmentId(httpServletRequest.getHeader(UserContext.DEPARTMENT_ID));
        userContextHolder.setEmployeeId(httpServletRequest.getHeader(UserContext.EMPLOYEE_ID));

        LOGGER.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}
