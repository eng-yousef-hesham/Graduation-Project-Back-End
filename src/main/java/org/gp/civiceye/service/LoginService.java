package org.gp.civiceye.service;

import jakarta.servlet.http.HttpServletResponse;
import org.gp.civiceye.mapper.LoginRequestDTO;
import org.gp.civiceye.mapper.LoginResponseDTO;
import org.gp.civiceye.mapper.UserDTO;

import java.util.List;

public interface LoginService {
    public UserDTO userInfo(String email, List<String> roles);
    LoginResponseDTO login(LoginRequestDTO requestDTO, HttpServletResponse response);
}
