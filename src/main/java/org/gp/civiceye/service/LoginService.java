package org.gp.civiceye.service;

import org.gp.civiceye.mapper.UserDTO;

import java.util.List;

public interface LoginService {
    public UserDTO userInfo(String email, List<String> roles);
}
