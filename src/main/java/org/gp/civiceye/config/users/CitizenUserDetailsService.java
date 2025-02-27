package org.gp.civiceye.config.users;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.repository.CitizenRepository;
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
public class CitizenUserDetailsService implements UserDetailsService {
    CitizenRepository citizenRepository;

    @Autowired
    public CitizenUserDetailsService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Citizen citizen = citizenRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("User not found for username: "+username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("Citizen"));
        String password = new String(citizen.getPasswordHash());
        User user = new User(citizen.getEmail(),password,authorities);
        return user;
    }
}