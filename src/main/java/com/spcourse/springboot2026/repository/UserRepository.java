package com.spcourse.springboot2026.repository;

import com.spcourse.springboot2026.entity.SchoolUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<SchoolUser, Long> {
    Optional<SchoolUser> findByUsername(String username);
}
