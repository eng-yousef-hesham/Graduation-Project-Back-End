package org.gp.civiceye.config.users;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.repository.entity.CityAdmin;
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
public class CityAdminUserDetailsService implements UserDetailsService {

    CityAdminRepository cityAdminRepository;

    public CityAdminUserDetailsService(CityAdminRepository cityAdminRepository) {
        this.cityAdminRepository = cityAdminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CityAdmin cityAdmin= cityAdminRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("User not found for username: "+username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("CityAdmin"));
        String password = new String(cityAdmin.getPasswordHash());
        User user = new User(cityAdmin.getEmail(),password,authorities);
        return user;
    }
}
