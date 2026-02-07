package com.spcourse.springboot2026.service;

import com.spcourse.springboot2026.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    CourseDTO create(CourseDTO dto);
    CourseDTO update(Long id, CourseDTO dto);
    List<CourseDTO> getList();
    List<CourseDTO> getCourseListByTeacherId(Long teacherId);
    void delete(Long id);
}
