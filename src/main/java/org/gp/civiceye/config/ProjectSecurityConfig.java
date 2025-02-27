package org.gp.civiceye.config;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.config.authproviders.CitizenUsernamePasswordAuthenticationProvider;
import org.gp.civiceye.config.authproviders.EmployeeUsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Slf4j
public class ProjectSecurityConfig {

    //Authentication providers

    private final CitizenUsernamePasswordAuthenticationProvider CitizenUsernamePasswordAuthenticationProvider;
    private final EmployeeUsernamePasswordAuthenticationProvider EmployeeUsernamePasswordAuthenticationProvider;


    @Autowired
    public  ProjectSecurityConfig(CitizenUsernamePasswordAuthenticationProvider CitizenUsernamePasswordAuthenticationProvider,EmployeeUsernamePasswordAuthenticationProvider employeeUsernamePasswordAuthenticationProvider) {
        this.CitizenUsernamePasswordAuthenticationProvider = CitizenUsernamePasswordAuthenticationProvider;
        this.EmployeeUsernamePasswordAuthenticationProvider = employeeUsernamePasswordAuthenticationProvider;
    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/**", "/api/V1/cityadmin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/V1/cityadmin").permitAll() // Explicitly allowing POST
                        .anyRequest().authenticated()
                );
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
    //CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000/"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(CitizenUsernamePasswordAuthenticationProvider, EmployeeUsernamePasswordAuthenticationProvider));
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//
//    }



//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker() {
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

}
