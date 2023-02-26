package com.habilisoft.doce.api.web.permits;

import com.habilisoft.doce.api.domain.model.EmployeePermit;
import com.habilisoft.doce.api.domain.services.EmployeePermitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 18/2/23.
 */
@RestController
@RequestMapping("/permits")
@RequiredArgsConstructor
public class EmployeePermitResource {
    private final EmployeePermitService permitService;

    @PostMapping
    public ResponseEntity<?> createPermit(@RequestBody EmployeePermit permit) {
        permitService.createPermit(permit);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPermit(@RequestBody EmployeePermit permit) {
        permitService.editPermit(permit);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermit(@PathVariable Long id) {
        permitService.deletePermit(id);
        return ResponseEntity.ok().build();
    }
}
