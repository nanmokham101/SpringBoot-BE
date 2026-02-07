package com.spcourse.springboot2026.service.impl;

import com.spcourse.springboot2026.config.SecurityUtil;
import com.spcourse.springboot2026.dto.TeacherDTO;
import com.spcourse.springboot2026.entity.Status;
import com.spcourse.springboot2026.entity.Teacher;
import com.spcourse.springboot2026.repository.TeacherRepository;
import com.spcourse.springboot2026.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherDTO create(TeacherDTO dto) {

        Teacher teacher = new Teacher();
        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPhoneNumber(dto.getPhoneNumber());
        teacher.setAddress(dto.getAddress());
        teacher.setRole(dto.getRole());

        teacher.setStatus(Status.ACTIVE);
        teacher.setCreatedDate(LocalDateTime.now());
        teacher.setCreatedBy(SecurityUtil.getCurrentUsername());

        return mapToDTO(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTO update(Long id, TeacherDTO dto) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPhoneNumber(dto.getPhoneNumber());
        teacher.setAddress(dto.getAddress());
        
        // Only update role if it's provided in the request
        if (dto.getRole() != null) {
            teacher.setRole(dto.getRole());
        }

        teacher.setUpdatedDate(LocalDateTime.now());
        teacher.setUpdatedBy(SecurityUtil.getCurrentUsername());

        return mapToDTO(teacherRepository.save(teacher));
    }

    @Override
    public List<TeacherDTO> getList() {
        return teacherRepository.findAll()
                .stream()// selfstudy this java 8 feature
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);
        Teacher teacher = teacherOptional.get();
        teacher.setStatus(Status.DELETE);
        teacherRepository.save(teacher);
    }

    private TeacherDTO mapToDTO(Teacher teacher) {

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
