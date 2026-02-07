package com.spcourse.springboot2026.dto;

import com.spcourse.springboot2026.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseAuditableDTO {
    private Status status;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}
