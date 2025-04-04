package org.gp.civiceye.config.users;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.Employee;
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
        Employee employee = EmployeeRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("User not found for username: "+username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        String password = new String(employee.getPasswordHash());
        User user = new User(employee.getEmail(),password,authorities);
        return user;
    }
}
