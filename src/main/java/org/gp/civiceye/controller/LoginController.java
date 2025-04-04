package org.gp.civiceye.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.gp.civiceye.constants.ApplicationConstants;
import org.gp.civiceye.mapper.LoginRequestDTO;
import org.gp.civiceye.mapper.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V1")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final Environment env;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, Environment env) {
        this.authenticationManager = authenticationManager;
        this.env = env;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        String jwt ;
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(),
                loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);
        if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VaLUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt =  Jwts.builder().issuer("Civiceye").subject("JWTTOken")
                        .claim("username", authenticationResponse.getName())
                        .claim("roles", authenticationResponse.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 1000 * 60 * 60 * 24 * 14))
                        .signWith(secretKey).compact();
                return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, jwt)
                        .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
            }
        }

        return null;
    }

}
