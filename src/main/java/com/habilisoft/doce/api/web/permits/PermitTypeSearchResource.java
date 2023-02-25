package com.habilisoft.doce.api.web.permits;

import com.habilisoft.doce.api.auth.base.resources.BaseResource;
import com.habilisoft.doce.api.auth.base.services.BaseService;
import com.habilisoft.doce.api.persistence.entities.PermitTypeEntity;
import com.habilisoft.doce.api.persistence.services.PermitTypeSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 18/2/23.
 */
@RestController
@RequestMapping("/permits/types")
@RequiredArgsConstructor
public class PermitTypeSearchResource extends BaseResource<PermitTypeEntity, Long> {
    private final PermitTypeSearchService permitTypeSearchService;

    @Override
    public BaseService<PermitTypeEntity, Long> getService() {
        return permitTypeSearchService;
    }

    @Override
    public String[] getSortableFields() {
        return new String[]{"id", "description"};
    }
}
