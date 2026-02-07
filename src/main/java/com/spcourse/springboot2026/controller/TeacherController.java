package com.spcourse.springboot2026.controller;
import com.spcourse.springboot2026.dto.TeacherDTO;
import com.spcourse.springboot2026.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<TeacherDTO> create(@RequestBody TeacherDTO dto) {
        return ResponseEntity.ok(teacherService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<TeacherDTO> update(
            @PathVariable Long id,
            @RequestBody TeacherDTO dto) {
        return ResponseEntity.ok(teacherService.update(id, dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<List<TeacherDTO>> list() {
        return ResponseEntity.ok(teacherService.getList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

