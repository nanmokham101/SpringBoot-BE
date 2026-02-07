package com.spcourse.springboot2026.service;

import com.spcourse.springboot2026.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {

    TeacherDTO create(TeacherDTO dto);
    TeacherDTO update(Long id, TeacherDTO dto);
    List<TeacherDTO> getList();
    void delete(Long id);
}

