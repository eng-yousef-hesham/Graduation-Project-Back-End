package org.gp.civiceye.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests)->requests.requestMatchers("").authenticated()
                .requestMatchers("").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
       UserDetails user = User.withUsername("yousef").password("{noop}12345Yyousef#").authorities("read").build();

       //password is a 12345 but encoded with bcrypt encoder
        // we can also remove the {bcrypt} prefix because it's the default in createDelegatingPasswordEncoder() or we can say in spring security framework
       UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$ettFa1L0VLCQTVLJYMuq4.ihPXa8p9.skEY/B4Twiqe/s3iA.C4zK").authorities("admin").build();

       return new InMemoryUserDetailsManager(user, admin);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
