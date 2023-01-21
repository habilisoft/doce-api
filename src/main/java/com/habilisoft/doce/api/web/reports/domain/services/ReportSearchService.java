package com.habilisoft.doce.api.web.reports.domain.services;

import com.habilisoft.doce.api.config.multitenant.TenantContext;
import com.habilisoft.doce.api.reporting.domain.model.Report;
import com.habilisoft.doce.api.reporting.domain.model.ReportQueryFilter;
import com.habilisoft.doce.api.reporting.domain.model.ReportSearchRequest;
import com.habilisoft.doce.api.reporting.domain.repositories.ReportRepository;
import com.habilisoft.doce.api.reporting.exceptions.InvalidQueryFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created on 15/1/23.
 */
@Service
@RequiredArgsConstructor
public class ReportSearchService {
    private final ReportRepository repository;
    private final JdbcTemplate jdbcTemplate;

    public Page<?> search(ReportSearchRequest  request) {
        Report report = repository.getQueryBySlug(request.getReportSlug());
        return search(report, request);
    }

    public Page<?> search(Report report, ReportSearchRequest request) {
        String searchQuery = buildSearchQuery(report, request);
        String countQuery = completeQuery(report.getCountQuery(), request, report.getQueryFilters());
        Long total = jdbcTemplate.queryForObject(countQuery, Long.class);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(searchQuery);
        return new PageImpl<>(
                result,
                PageRequest.of(request.getPage(), request.getSize()),
                Optional.ofNullable(total).orElse(0L)
        );
    }

    public List<Map<String, Object>> all(Report report, ReportSearchRequest request) {
        String query = completeQuery(report.getQuery(), request, report.getQueryFilters());
        query = applySorting(query, report.getDefaultOrder());
        return jdbcTemplate.queryForList(query);
    }

    public String buildSearchQuery(Report report, ReportSearchRequest request) {
        String query = completeQuery(report.getQuery(), request, report.getQueryFilters());
        query = applySorting(query, report.getDefaultOrder());
        return applyPagination(query, request);
    }
    private String completeQuery(String query, ReportSearchRequest request, List<ReportQueryFilter> queryFilters) {
        String schema = TenantContext.getCurrentTenant();
        query = query.replace("[schema]", schema);
        return query.concat(buildFilters(request.getQueryMap(), queryFilters));
    }

    private String applySorting(String query, String defaultSort) {
        return String.format(" %s order by %s", query, defaultSort);
    }

    private String applyPagination(String query, ReportSearchRequest request) {
        return String.format(
                " %s limit %s offset %s ",
                query,
                request.getSize(),
                request.getPage() * request.getSize()
        );
    }

    private String buildFilters(Map<String, Object> queryMap, List<ReportQueryFilter> queryFilters) {
        if(queryMap.isEmpty()) {
            return "";
        }
        return " WHERE " + queryMap.entrySet()
                .stream()
                .map( entry -> {
                    ReportQueryFilter queryFilter = queryFilters.stream()
                            .filter(f -> f.getUiField().equals(entry.getKey()))
                            .findFirst()
                            .orElseThrow(()-> new InvalidQueryFieldException(entry.getKey()));
                    Object param = getParam(queryFilter.getType(), entry.getValue());
                    return String.format("%s=%s", queryFilter.getQueryField(), param);
                })
                .collect(Collectors.joining(" AND "));
    }

    private Object getParam(ReportQueryFilter.Type type, Object value) {
        if(type == null) {
            return value;
        }
        return switch (type) {
            case DATE -> String.format("'%s'", value);
            //add more types in the future
            default -> value;
        };
    }
}
