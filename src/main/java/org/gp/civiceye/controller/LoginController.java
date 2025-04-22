package org.gp.civiceye.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.gp.civiceye.constants.ApplicationConstants;
import org.gp.civiceye.mapper.LoginRequestDTO;
import org.gp.civiceye.mapper.LoginResponseDTO;
import org.gp.civiceye.mapper.UserDTO;
import org.gp.civiceye.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V1")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final LoginService loginService;


    @Autowired
    public LoginController(AuthenticationManager authenticationManager, Environment env,LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.env = env;
        this.loginService =loginService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        String jwt;
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            if (env != null) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                List<String> roles =authenticationResponse.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                jwt = Jwts.builder()
                        .issuer("Civiceye")
                        .subject(authenticationResponse.getName())
                        .claim("username", authenticationResponse.getName())
                        .claim("roles", roles)
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 14)) // 14 days
                        .signWith(secretKey)
                        .compact();

                // ✅ Create HTTP-only cookie
                ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                        .httpOnly(true)
                        .secure(true) // set to true in production (requires HTTPS)
                        .path("/")
                        .maxAge(Duration.ofDays(14))
                        .sameSite("None")// or Lax/None depending on your app's needs
                        .build();

                // ✅ Add cookie to response
                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                String type = mapRoleToType(roles);


                LoginResponseDTO responseBody = new LoginResponseDTO(
                        "Login successful",
                        authenticationResponse.getName(),
                        type
                );

                return ResponseEntity.ok(responseBody);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
    private String mapRoleToType(List<String> roles) {
        if (roles.contains("ROLE_MASTERADMIN")) return "MasterAdmin";
        if (roles.contains("ROLE_GOVERNORATEADMIN")) return "GovernorateAdmin";
        if (roles.contains("ROLE_CITYADMIN")) return "CityAdmin";
        if (roles.contains("ROLE_EMPLOYEE")) return "Employee";
        if (roles.contains("ROLE_CITIZEN")) return "Citizen";
        return "Unknown";
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("User is logged in as: " + authentication.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }
    }


    @GetMapping("/user")
    public ResponseEntity<UserDTO> userInfo(Authentication authentication) {

        UserDTO userDTO =null;

        if (authentication != null && authentication.isAuthenticated()) {
             userDTO = loginService.userInfo(authentication.getName()
                    ,authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userDTO);
        }

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(0) // delete cookie
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logged out");
    }


}
