package com.spcourse.springboot2026.service;

import com.spcourse.springboot2026.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO create(StudentDTO student);
    StudentDTO update(Long id, StudentDTO student);
    List<StudentDTO> getList();
    void delete(Long id);
}

