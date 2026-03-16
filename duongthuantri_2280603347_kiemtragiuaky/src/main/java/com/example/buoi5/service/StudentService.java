package com.example.buoi5.service;

import com.example.buoi5.model.Student;

public interface StudentService {
    Student register(Student student);

    Student findByUsername(String username);

    Student findByEmail(String email);
}
