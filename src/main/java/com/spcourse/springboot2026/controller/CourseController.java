package com.spcourse.springboot2026.controller;

import com.spcourse.springboot2026.dto.CourseDTO;
import com.spcourse.springboot2026.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(
            @PathVariable Long id,
            @RequestBody CourseDTO dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> list() {
        return ResponseEntity.ok(courseService.getList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

