package com.spcourse.springboot2026.controller;

import com.spcourse.springboot2026.dto.StudentDTO;
import com.spcourse.springboot2026.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.create(studentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(
            @PathVariable Long id,
            @RequestBody StudentDTO studentDTO) {

        return ResponseEntity.ok(studentService.update(id, studentDTO));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getList() {
        return ResponseEntity.ok(studentService.getList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
