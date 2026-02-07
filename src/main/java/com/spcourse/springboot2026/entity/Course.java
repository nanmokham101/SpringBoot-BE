package com.spcourse.springboot2026.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Course extends BaseAuditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String courseName;

    @ManyToOne
    @JoinColumn(
            name = "teacher_id",
            foreignKey = @ForeignKey(name = "fk_course_teacher")
    )
    private Teacher assignedTeacher;
}

