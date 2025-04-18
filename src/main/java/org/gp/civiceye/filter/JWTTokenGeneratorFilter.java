package org.gp.civiceye.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gp.civiceye.constants.ApplicationConstants;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Environment env = getEnvironment();
            if (env != null) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                String jwt = Jwts.builder()
                        .issuer("Civiceye")
                        .subject("JWTToken")
                        .claim("username", authentication.getName())
                        .claim("roles", authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 14)) // 14 days
                        .signWith(secretKey)
                        .compact();

                // ✅ Set the JWT in an HTTP-only cookie
                Cookie cookie = new Cookie("jwt", jwt);
                cookie.setHttpOnly(true); // Prevent JavaScript access
                cookie.setSecure(false);   // Set to true in production with HTTPS
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 14); // 14 days
                response.addCookie(cookie);

                // Optional: Still add header if you want both
                // response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
