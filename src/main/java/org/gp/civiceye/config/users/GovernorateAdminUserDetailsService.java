package org.gp.civiceye.config.users;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.repository.GovernorateAdminRepository;
import org.gp.civiceye.repository.entity.GovernorateAdmin;
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
public class GovernorateAdminUserDetailsService implements UserDetailsService {

    GovernorateAdminRepository  governorateAdminRepository;

    public GovernorateAdminUserDetailsService(GovernorateAdminRepository  governorateAdminRepository ) {
        this.governorateAdminRepository = governorateAdminRepository ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GovernorateAdmin governorateAdmin= governorateAdminRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("User not found for username: "+username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("CityAdmin"),new SimpleGrantedAuthority("GovernorateAdmin"));
        String password = new String(governorateAdmin.getPasswordHash());
        User user = new User(governorateAdmin.getEmail(),password,authorities);
        return user;
    }
}
