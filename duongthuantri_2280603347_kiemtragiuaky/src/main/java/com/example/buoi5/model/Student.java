package com.example.buoi5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    @Column(nullable = false, unique = true)
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "student_role", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
