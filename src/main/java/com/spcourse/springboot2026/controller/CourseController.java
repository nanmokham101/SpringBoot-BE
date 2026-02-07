package com.spcourse.springboot2026.controller;

import com.spcourse.springboot2026.dto.CourseDTO;
import com.spcourse.springboot2026.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<CourseDTO> update(
            @PathVariable Long id,
            @RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<List<CourseDTO>> list() {
        return ResponseEntity.ok(courseService.getList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEADMASTER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

