package com.spcourse.springboot2026.service.impl;

import com.spcourse.springboot2026.config.SecurityUtil;
import com.spcourse.springboot2026.dto.CourseDTO;
import com.spcourse.springboot2026.dto.TeacherDTO;
import com.spcourse.springboot2026.entity.Course;
import com.spcourse.springboot2026.entity.Status;
import com.spcourse.springboot2026.entity.Teacher;
import com.spcourse.springboot2026.repository.CourseRepository;
import com.spcourse.springboot2026.repository.TeacherRepository;
import com.spcourse.springboot2026.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public CourseDTO create(CourseDTO dto) {

        Course course = new Course();
        course.setCourseName(dto.getCourseName());

        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            course.setAssignedTeacher(teacher);
        }

        course.setStatus(Status.ACTIVE);
        course.setCreatedDate(LocalDateTime.now());
        course.setCreatedBy(SecurityUtil.getCurrentUsername());

        return mapToDTO(courseRepository.save(course));
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(dto.getCourseName());

        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            course.setAssignedTeacher(teacher);
        }

        course.setUpdatedDate(LocalDateTime.now());
        course.setUpdatedBy(SecurityUtil.getCurrentUsername());

        return mapToDTO(courseRepository.save(course));
    }

    @Override
    public List<CourseDTO> getList() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Course course = courseOptional.get();
        course.setStatus(Status.DELETE);
        courseRepository.save(course);
    }

    private CourseDTO mapToDTO(Course course) {

        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseName(course.getCourseName());

        if (course.getAssignedTeacher() != null) {
            dto.setTeacherId(course.getAssignedTeacher().getId());
            dto.setTeacher(mapTeacherToDTO(course.getAssignedTeacher()));
        }

        dto.setStatus(course.getStatus());
        dto.setCreatedDate(course.getCreatedDate());
        dto.setCreatedBy(course.getCreatedBy());
        dto.setUpdatedDate(course.getUpdatedDate());
        dto.setUpdatedBy(course.getUpdatedBy());

        return dto;
    }
    
    private TeacherDTO mapTeacherToDTO(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setEmail(teacher.getEmail());
        dto.setPhoneNumber(teacher.getPhoneNumber());
        dto.setAddress(teacher.getAddress());
        dto.setRole(teacher.getRole());
        dto.setStatus(teacher.getStatus());
        dto.setCreatedDate(teacher.getCreatedDate());
        dto.setCreatedBy(teacher.getCreatedBy());
        dto.setUpdatedDate(teacher.getUpdatedDate());
        dto.setUpdatedBy(teacher.getUpdatedBy());
        return dto;
    }
}
