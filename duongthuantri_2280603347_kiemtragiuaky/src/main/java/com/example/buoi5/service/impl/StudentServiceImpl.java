package com.example.buoi5.service.impl;

import com.example.buoi5.model.Role;
import com.example.buoi5.model.Student;
import com.example.buoi5.repository.RoleRepository;
import com.example.buoi5.repository.StudentRepository;
import com.example.buoi5.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Student register(Student student) {
        if (studentRepository.existsByUsername(student.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new RuntimeException("STUDENT role not found"));
        student.setRoles(Set.of(studentRole));
        return studentRepository.save(student);
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }
}
