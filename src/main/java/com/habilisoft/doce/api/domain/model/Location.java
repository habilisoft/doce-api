package com.habilisoft.doce.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Created on 2019-04-27.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String googleAddress;

    public Location(Long id) {
        this.id = id;
    }

    public static Location ofId(Long id) {
        return new Location(id);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        return Objects.equals(this.id, other.id);
    }

}
