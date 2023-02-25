package com.habilisoft.doce.api.web.employees.resources;

import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.persistence.entities.BaseEmployee;
import com.habilisoft.doce.api.persistence.entities.EmployeeEntity;
import com.habilisoft.doce.api.persistence.services.BaseEmployeeSearchService;
import com.habilisoft.doce.api.persistence.services.EmployeeSearchService;
import com.habilisoft.doce.api.web.employees.converters.EmployeeEntityHttpConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 29/11/22.
 */
@RestController
@RequiredArgsConstructor
public class EmployeeSearchResource {
    private final EmployeeSearchService searchService;
    private final EmployeeEntityHttpConverter converter;
    private final BaseEmployeeSearchService baseEmployeeSearchService;

    @GetMapping("/employees")
    public Page<?> searchEmployees(@RequestParam final Map<String, Object> queryMap,
                                             @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                             @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                             @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "fullName";

        Page<EmployeeEntity> entityPage =  searchService.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, new String[]{"fullName"})
                )
        );

        return entityPage.map(converter::toHttpResponse);
    }

    @GetMapping("/employees/search-box")
    public Page<?> searchBaseEmployees(@RequestParam final Map<String, Object> queryMap,
                                             @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                             @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                             @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "fullName";

        Page<BaseEmployee> entityPage =  baseEmployeeSearchService.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, new String[]{"fullName"})
                )
        );

        return entityPage.map(converter::toHttpResponse);
    }
}
