package com.habilisoft.doce.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2019-04-27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    private Long id;
    private String name;
    private Group(Long id) {
        this.id = id;
    }
    public static Group ofId(Long id){
        return new Group(id);
    }
}
