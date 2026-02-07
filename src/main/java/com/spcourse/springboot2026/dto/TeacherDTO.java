package com.spcourse.springboot2026.dto;

import com.spcourse.springboot2026.entity.Role;
import lombok.Data;

@Data
public class TeacherDTO extends BaseAuditableDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Role role;
}

