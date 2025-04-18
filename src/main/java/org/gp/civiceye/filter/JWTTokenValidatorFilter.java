package org.gp.civiceye.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gp.civiceye.constants.ApplicationConstants;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = null;

        // ✅ 1. Extract token from the "jwt" cookie
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null) {
            try {
                Environment env = getEnvironment();
                if (env != null) {
                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                            ApplicationConstants.JWT_SECRET_DEFAULT_VaLUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                    Claims claims = Jwts.parser()
                            .verifyWith(secretKey)
                            .build()
                            .parseSignedClaims(jwt)
                            .getPayload();

                    String username = String.valueOf(claims.get("username"));
                    List<String> rolesList = claims.get("roles", List.class);
                    List<SimpleGrantedAuthority> authorities = rolesList.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(String.format(
                        "{" +
                                "\"timestamp\":\"%s\"," +
                                "\"status\":401," +
                                "\"error\":\"Unauthorized\"," +
                                "\"message\":\"Invalid or expired token\"," +
                                "\"path\":\"%s\"" +
                                "}",
                        java.time.Instant.now().toString(),
                        request.getRequestURI()
                ));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
