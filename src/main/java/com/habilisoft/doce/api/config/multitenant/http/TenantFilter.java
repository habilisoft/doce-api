package com.habilisoft.doce.api.config.multitenant.http;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Daniel
 */
@Component
public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "TenantID";
    private static final String HEALTH_CHECK_URL = "/healthcheck";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String tenantHeader = req.getHeader(TENANT_HEADER);

        if (!req.getRequestURI().contains(HEALTH_CHECK_URL)) {
            if (tenantHeader != null && !tenantHeader.isEmpty()) {
                TenantContext.setCurrentTenant(tenantHeader);
            } else {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("error", "No tenant header supplied");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(objectNode.toString());
                response.getWriter().flush();
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
