package com.habilisoft.doce.api.domain.commands;

import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.model.WorkShift;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateEmployee {
    private Long id;
    private Integer enrollId;
    private String fullName;
    private String documentNumber;
    private String externalId;
    private Group group;
    private Location location;
    private WorkShift workShift;
}
