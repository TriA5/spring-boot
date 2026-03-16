package com.example.buoi5.security;

import com.example.buoi5.model.Role;
import com.example.buoi5.model.Student;
import com.example.buoi5.repository.RoleRepository;
import com.example.buoi5.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");

        studentRepository.findByEmail(email).orElseGet(() -> {
            Student newStudent = new Student();
            newStudent.setEmail(email);
            String username = email.split("@")[0];
            if (studentRepository.existsByUsername(username)) {
                username = username + "_" + UUID.randomUUID().toString().substring(0, 5);
            }
            newStudent.setUsername(username);
            newStudent.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            Role studentRole = roleRepository.findByName("STUDENT")
                    .orElseThrow(() -> new RuntimeException("STUDENT role not found"));
            newStudent.setRoles(Set.of(studentRole));
            return studentRepository.save(newStudent);
        });

        return oauth2User;
    }
}
