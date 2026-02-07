package com.spcourse.springboot2026.repository;

import com.spcourse.springboot2026.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByAssignedTeacherId(Long teacherId);
}
