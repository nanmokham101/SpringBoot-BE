package com.spcourse.springboot2026.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "school_users")
@Data // = GETTER, SETTER
public class SchoolUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}