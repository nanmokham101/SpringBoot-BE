package com.spcourse.springboot2026.service.impl;

import com.spcourse.springboot2026.config.SecurityUtil;
import com.spcourse.springboot2026.dto.CourseDTO;
import com.spcourse.springboot2026.dto.StudentDTO;
import com.spcourse.springboot2026.dto.TeacherDTO;
import com.spcourse.springboot2026.entity.Course;
import com.spcourse.springboot2026.entity.Status;
import com.spcourse.springboot2026.entity.Student;
import com.spcourse.springboot2026.entity.Teacher;
import com.spcourse.springboot2026.repository.CourseRepository;
import com.spcourse.springboot2026.repository.StudentRepository;
import com.spcourse.springboot2026.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if (student.getCourses() != null && !student.getCourses().isEmpty()) {
            // Set course ID list
            dto.setCourseIdList(
                    student.getCourses()
                            .stream()
                            .map(Course::getId)
                            .toList()
            );
            
            // Set full course info with teacher
            dto.setCourses(
                    student.getCourses()
                            .stream()
                            .map(this::mapCourseToDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
    
    private CourseDTO mapCourseToDTO(Course course) {
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
