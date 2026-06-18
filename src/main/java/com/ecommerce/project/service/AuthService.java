package com.ecommerce.project.service;

import com.ecommerce.project.dto.LoginDTO;
import com.ecommerce.project.dto.UserDTO;

public interface AuthService {

    String register(UserDTO userDTO);

    String login(LoginDTO loginDTO);
}