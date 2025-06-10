package org.gp.civiceye.config;

import lombok.extern.slf4j.Slf4j;
import org.gp.civiceye.config.authproviders.*;
import org.gp.civiceye.filter.JWTTokenGeneratorFilter;
import org.gp.civiceye.filter.JWTTokenValidatorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
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
    private final CityAdminUsernamePasswordAuthenticationProvider CityAdminUsernamePasswordAuthenticationProvider;
    private final GovernorateAdminUsernamePasswordAuthenticationProvider GovernorateAdminUsernamePasswordAuthenticationProvider;
    private final MasterAdminUsernamePasswordAuthenticationProvider MasterAdminUsernamePasswordAuthenticationProvider;;


    @Autowired
    public  ProjectSecurityConfig(CitizenUsernamePasswordAuthenticationProvider CitizenUsernamePasswordAuthenticationProvider,
                                  EmployeeUsernamePasswordAuthenticationProvider employeeUsernamePasswordAuthenticationProvider,
                                  CityAdminUsernamePasswordAuthenticationProvider CityAdminUsernamePasswordAuthenticationProvider,
                                  GovernorateAdminUsernamePasswordAuthenticationProvider GovernorateAdminUsernamePasswordAuthenticationProvider,
                                  MasterAdminUsernamePasswordAuthenticationProvider MasterAdminUsernamePasswordAuthenticationProvider
                                  ) {
        this.CitizenUsernamePasswordAuthenticationProvider = CitizenUsernamePasswordAuthenticationProvider;
        this.EmployeeUsernamePasswordAuthenticationProvider = employeeUsernamePasswordAuthenticationProvider;
        this.CityAdminUsernamePasswordAuthenticationProvider = CityAdminUsernamePasswordAuthenticationProvider;
        this.GovernorateAdminUsernamePasswordAuthenticationProvider = GovernorateAdminUsernamePasswordAuthenticationProvider;
        this.MasterAdminUsernamePasswordAuthenticationProvider = MasterAdminUsernamePasswordAuthenticationProvider;

    }



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionconfig->sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/V1/masteradmins").hasRole("MASTERADMIN")
                        .requestMatchers("/api/V1/admin").hasRole("MASTERADMIN")
                        .requestMatchers("/api/V1/city/").hasAnyRole("MASTERADMIN")
                        .requestMatchers("/api/V1/cities/").hasAnyRole("MASTERADMIN", "CITIZEN","CITYADMIN","GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/departments").hasAnyRole("MASTERADMIN", "CITIZEN","CITYADMIN","GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/governorates").hasAnyRole("MASTERADMIN", "CITIZEN","CITYADMIN","GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/governorateadmin").hasRole("GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/employee/gov").hasRole("GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/cityadmins").hasRole("CITYADMIN")
                        .requestMatchers("/api/V1/employee/city").hasRole("CITYADMIN")
                        .requestMatchers("/api/V1/employees").hasAnyRole("MASTERADMIN","EMPLOYEE","CITYADMIN","GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/citizen").hasRole("CITIZEN")
                        .requestMatchers("/api/V1/reports/**").hasAnyRole("EMPLOYEE", "CITIZEN","MASTERADMIN","CITYADMIN","GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/report-statuses").hasAnyRole("EMPLOYEE", "CITIZEN","MASTERADMIN","CITYADMIN","GOVERNORATEADMIN")
                        .requestMatchers("/api/V1/check").hasAnyRole(
                                "MASTERADMIN",
                                "GOVERNORATEADMIN",
                                "CITYADMIN",
                                "EMPLOYEE",
                                "CITIZEN"
                        )
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/V1/login",
                                "/api/V1/citizenRegister",
                                "/ws"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationManager(authenticationManager());
        http.formLogin(formLoginConfigurer->formLoginConfigurer.disable());
        http.httpBasic(hbc->hbc.disable());
        return http.build();
    }
    //CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000","https://graduation-front-1.onrender.com","http://localhost:8000","http://localhost:2000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(86400L);

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

        ProviderManager providerManager = new ProviderManager(Arrays.asList(
                CitizenUsernamePasswordAuthenticationProvider,
                EmployeeUsernamePasswordAuthenticationProvider,
                CityAdminUsernamePasswordAuthenticationProvider,
                GovernorateAdminUsernamePasswordAuthenticationProvider,
                MasterAdminUsernamePasswordAuthenticationProvider));
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
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
