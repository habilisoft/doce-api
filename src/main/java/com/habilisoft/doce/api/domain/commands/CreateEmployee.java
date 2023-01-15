package com.habilisoft.doce.api.domain.commands;

import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.model.LocationType;
import com.habilisoft.doce.api.domain.model.WorkShift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 11/11/22.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployee {
    private String fullName;
    private String documentNumber;
    private Integer enrollId;
    private Group group;
    private Long locationId;
    private LocationType locationType;
    private WorkShift workShift;
}
