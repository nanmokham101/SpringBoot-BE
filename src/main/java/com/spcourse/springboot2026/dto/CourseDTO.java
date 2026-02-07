package com.spcourse.springboot2026.dto;

import lombok.Data;

@Data
public class CourseDTO extends BaseAuditableDTO {

    private Long id;
    private String courseName;

    // assigned teacher
    private Long teacherId;
    private String teacherName;
}
