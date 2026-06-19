package com.ecommerce.project.service;

import com.ecommerce.project.dto.LoginDTO;
import com.ecommerce.project.dto.UserDTO;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(UserDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        // encrypt password
        user.setPassword(encoder.encode(dto.getPassword()));

        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public String login(LoginDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            return "Invalid password";
        }

        // GENERATE TOKEN
        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return token;
    }
}
