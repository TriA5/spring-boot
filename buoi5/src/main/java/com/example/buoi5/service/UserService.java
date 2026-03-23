package com.example.buoi5.service;

import com.example.buoi5.dto.RegisterDTO;
import com.example.buoi5.model.User;

import java.util.Optional;

public interface UserService {
    User register(RegisterDTO dto);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
