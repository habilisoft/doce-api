package com.habilisoft.doce.api.web.permits;

import com.habilisoft.doce.api.auth.base.utils.SortUtils;
import com.habilisoft.doce.api.persistence.entities.EmployeePermitEntity;
import com.habilisoft.doce.api.persistence.services.EmployeePermitSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 19/2/23.
 */
@RestController
@RequiredArgsConstructor
public class EmployeePermitSearchResource  {
    private final EmployeePermitSearchService employeePermitSearchService;

    @GetMapping("/permits")
    public Page<?> searchEmployees(@RequestParam final Map<String, Object> queryMap,
                                   @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                   @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                   @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "createdDate";

        Page<EmployeePermitEntity> entityPage = employeePermitSearchService.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, new String[]{"createdDate"})
                )
        );

        return entityPage;

    }
}
