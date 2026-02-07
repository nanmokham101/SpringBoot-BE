package com.spcourse.springboot2026.dto;
import lombok.Data;

import java.util.List;
@Data
public class StudentDTO extends BaseAuditableDTO {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private List<Long> courseIdList;
}
