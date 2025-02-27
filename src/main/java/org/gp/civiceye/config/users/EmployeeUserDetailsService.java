package org.gp.civiceye.config.users;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeUserDetailsService implements UserDetailsService {
    EmployeeRepository EmployeeRepository;

    @Autowired
    public EmployeeUserDetailsService(EmployeeRepository employeeRepository) {
        this.EmployeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Citizen citizen = EmployeeRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("User not found for username: "+username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("Employee"));
        String password = new String(citizen.getPasswordHash());
        User user = new User(citizen.getEmail(),password,authorities);
        return user;
    }
}
