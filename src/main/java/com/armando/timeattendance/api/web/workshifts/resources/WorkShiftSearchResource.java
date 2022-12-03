package com.armando.timeattendance.api.web.workshifts.resources;

import com.armando.timeattendance.api.auth.base.utils.SortUtils;
import com.armando.timeattendance.api.domain.model.WorkShiftDay;
import com.armando.timeattendance.api.persistence.converters.WorkShiftJpaConverter;
import com.armando.timeattendance.api.persistence.entities.WorkShiftEntity;
import com.armando.timeattendance.api.persistence.services.WorkShiftSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created on 2019-04-27.
 */
@RestController
@RequestMapping("/work-shifts")
@RequiredArgsConstructor
public class WorkShiftSearchResource {
    private final WorkShiftSearchService service;
    private final WorkShiftJpaConverter converter;

    @RequestMapping(value = "/days", method = RequestMethod.GET)
    public ResponseEntity<?> getDays() {
        return ResponseEntity.ok(
                WorkShiftDay.values()
        );
    }

    @GetMapping
    public Page<?> searchEmployees(@RequestParam final Map<String, Object> queryMap,
                                   @RequestParam(name = "_page", required = false, defaultValue = "0") final Integer page,
                                   @RequestParam(name = "_size", required = false, defaultValue = "25") final Integer size,
                                   @RequestParam(name = "_sort", required = false, defaultValue = "") String sort) {

        if (!StringUtils.hasLength(sort))
            sort = "-" + "createdDate";

        Page<WorkShiftEntity> entityPage =  service.search(
                queryMap,
                PageRequest.of(
                        page,
                        size,
                        SortUtils.processSort(sort, getSortableFields())
                )
        );

        return entityPage.map(converter::fromJpaEntity);
    }


    public String[] getSortableFields() {
        return new String[]{"createdDate", "lastModifiedDate", "name"};
    }

}
