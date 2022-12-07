package com.habilisoft.doce.api.web.workshifts.resources;

import com.habilisoft.doce.api.domain.model.WorkShift;
import com.habilisoft.doce.api.domain.services.WorkShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2/12/22.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("work-shifts")
public class WorkShiftResource {
    private final WorkShiftService service;

    @PostMapping
    public ResponseEntity<WorkShift> save(@RequestBody WorkShift workShift) {
        workShift = service.save(workShift);
        return ResponseEntity.ok(workShift);
    }

    @PutMapping("/{workShiftId}")
    public ResponseEntity<WorkShift> edit(@RequestBody WorkShift workShift,
                                          @PathVariable Long workShiftId) {
        workShift = service.update(workShiftId, workShift);
        return ResponseEntity.ok(workShift);
    }

}
