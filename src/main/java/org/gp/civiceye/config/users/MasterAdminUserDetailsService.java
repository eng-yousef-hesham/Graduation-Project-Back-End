package org.gp.civiceye.config.users;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.repository.MasterAdminRepository ;
import org.gp.civiceye.repository.entity.GovernorateAdmin;
import org.gp.civiceye.repository.entity.MasterAdmin;
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
public class MasterAdminUserDetailsService implements UserDetailsService {

    MasterAdminRepository masterAdminRepository;

    public MasterAdminUserDetailsService(MasterAdminRepository   masterAdminRepository  ) {
        this.masterAdminRepository = masterAdminRepository  ;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MasterAdmin masterAdmin= masterAdminRepository.findByEmail(username).orElseThrow(()->new
                UsernameNotFoundException("User not found for username: "+username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_CITYADMIN"),new SimpleGrantedAuthority("ROLE_GOVERNORATEADMIN"),new SimpleGrantedAuthority("ROLE_MASTERADMIN"));
        String password = new String(masterAdmin.getPasswordHash());
        User user = new User(masterAdmin.getEmail(),password,authorities);
        return user;
    }
}
