package com.spcourse.springboot2026.service.impl;

import com.spcourse.springboot2026.config.SecurityUtil;
import com.spcourse.springboot2026.dto.StudentDTO;
import com.spcourse.springboot2026.entity.Course;
import com.spcourse.springboot2026.entity.Status;
import com.spcourse.springboot2026.entity.Student;
import com.spcourse.springboot2026.repository.CourseRepository;
import com.spcourse.springboot2026.repository.StudentRepository;
import com.spcourse.springboot2026.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public StudentDTO create(StudentDTO dto) {

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setAddress(dto.getAddress());

        if (dto.getCourseIdList() != null) {
            List<Course> courses = courseRepository.findAllById(dto.getCourseIdList());
            student.setCourses(courses);
        }
        student.setStatus(Status.ACTIVE);
        student.setCreatedDate(LocalDateTime.now());
        student.setCreatedBy(SecurityUtil.getCurrentUsername());


        Student saved = studentRepository.save(student);
        return mapToDTO(saved);
    }

    @Override
    public StudentDTO update(Long id, StudentDTO dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setAddress(dto.getAddress());

        if (dto.getCourseIdList() != null) {
            List<Course> courses = courseRepository.findAllById(dto.getCourseIdList());
            student.setCourses(courses);
        }

        student.setUpdatedDate(LocalDateTime.now());
        student.setUpdatedBy(SecurityUtil.getCurrentUsername());
        Student updated = studentRepository.save(student);
        return mapToDTO(updated);
    }

    @Override
    public List<StudentDTO> getList() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        Student student = studentOptional.get();
        student.setStatus(Status.DELETE);
        studentRepository.save(student);
    }

    // ===== mapper =====
    private StudentDTO mapToDTO(Student student) {

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setAddress(student.getAddress());

        dto.setStatus(student.getStatus());
        dto.setCreatedDate(student.getCreatedDate());
        dto.setCreatedBy(student.getCreatedBy());
        dto.setUpdatedDate(student.getUpdatedDate());
        dto.setUpdatedBy(student.getUpdatedBy());

        if (student.getCourses() != null) {
            dto.setCourseIdList(
                    student.getCourses()
                            .stream()
                            .map(Course::getId)
                            .toList()
            );
        }

        return dto;
    }

}
