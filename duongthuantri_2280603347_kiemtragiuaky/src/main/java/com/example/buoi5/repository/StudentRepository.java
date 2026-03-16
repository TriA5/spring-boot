package com.example.buoi5.repository;

import com.example.buoi5.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);

    Optional<Student> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
