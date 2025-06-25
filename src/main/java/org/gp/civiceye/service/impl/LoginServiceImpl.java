package org.gp.civiceye.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.gp.civiceye.constants.ApplicationConstants;
import org.gp.civiceye.exception.BannedUserException;
import org.gp.civiceye.exception.InvalidLoginRequestException;
import org.gp.civiceye.mapper.LoginRequestDTO;
import org.gp.civiceye.mapper.LoginResponseDTO;
import org.gp.civiceye.mapper.UserDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    private final CityAdminRepository cityAdminRepository;
    private final GovernorateAdminRepository governorateAdminRepository;
    private final MasterAdminRepository masterAdminRepository;
    private final CitizenRepository citizenRepository;
    private final EmployeeRepository employeeRepository;
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    @Autowired
    public LoginServiceImpl(CityAdminRepository cityAdminRepository, GovernorateAdminRepository governorateAdminRepository,
                            MasterAdminRepository masterAdminRepository, CitizenRepository cityAdminRepository1,
                            EmployeeRepository employeeRepository, AuthenticationManager authenticationManager, Environment env) {
        this.cityAdminRepository = cityAdminRepository;
        this.governorateAdminRepository = governorateAdminRepository;
        this.masterAdminRepository = masterAdminRepository;
        this.citizenRepository = cityAdminRepository1;
        this.employeeRepository = employeeRepository;
        this.authenticationManager = authenticationManager;
        this.env = env;
    }




    public UserDTO userInfo(String email, List<String> roles) {


        if(roles.contains("ROLE_MASTERADMIN"))
        {
           MasterAdmin MA = masterAdminRepository.findByEmail(email).orElseThrow(()->new
                   UsernameNotFoundException("User not found for email: "+email));
           return new UserDTO(MA.getAdminId(),MA.getNationalId(),MA.getFirstName()+" "+MA.getLastName(),MA.getFirstName()
                   ,MA.getLastName(),MA.getEmail(),null,null,null,roles,2000,null,null);


        } else if (roles.contains("ROLE_GOVERNORATEADMIN")) {
            GovernorateAdmin GA = governorateAdminRepository.findByEmail(email).orElseThrow(()->new
                    UsernameNotFoundException("User not found for email: "+email));
//            Governorate G = governorateAdminRepository.findGovernorateByEmail(email).orElse(null);
            return new UserDTO(GA.getAdminId(),GA.getNationalId(),GA.getFirstName()+" "+GA.getLastName(),GA.getFirstName()
                    ,GA.getLastName(),GA.getEmail(),GA.getGovernorate().getName(),null,null,roles,1999,null,GA.getGovernorate().getGovernorateId());


        } else if (roles.contains("ROLE_CITYADMIN")) {
            CityAdmin CA = cityAdminRepository.findByEmail(email).orElseThrow(()->new
                    UsernameNotFoundException("User not found for email: "+email));
            return new UserDTO(CA.getAdminId(),CA.getNationalId(),CA.getFirstName()+" "+CA.getLastName(),CA.getFirstName()
                    ,CA.getLastName(),CA.getEmail(),CA.getCity().getGovernorate().getName(),CA.getCity().getName(),null,roles,1998,CA.getCity().getCityId(),CA.getCity().getGovernorate().getGovernorateId());


        } else if (roles.contains("ROLE_EMPLOYEE")) {
            Employee E = employeeRepository.findByEmail(email).orElseThrow(()->new
                    UsernameNotFoundException("User not found for username: "+email));
            return new UserDTO(E.getEmpId(),E.getNationalId(),E.getFirstName()+" "+E.getLastName(),E.getFirstName()
                    ,E.getLastName(),E.getEmail(),E.getCity().getGovernorate().getName(),E.getCity().getName(),E.getDepartment().name(),roles,0,null,null);


        } else if (roles.contains("ROLE_CITIZEN")) {
            Citizen C = citizenRepository.findByEmail(email).orElseThrow(()->new
                    UsernameNotFoundException("User not found for username: "+email));
            return new UserDTO(C.getCitizenId(),C.getNationalId(),C.getFirstName()+" "+C.getLastName(),C.getFirstName()
                    ,C.getLastName(),C.getEmail(),null,null,null,roles,0,null,null);

        }
        return null;


    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO, HttpServletResponse response) {
        String jwt;
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(
                requestDTO.username(), requestDTO.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            if (env != null) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                List<String> roles = authenticationResponse.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                if (roles.contains("ROLE_CITIZEN")) {
                    Citizen citizen = citizenRepository.findByEmail(requestDTO.username()).get();
                    if (!citizen.getIsActive()) {
                        throw new BannedUserException("User with email " + requestDTO.username() + " is banned. Please contact the admin.");
                    }
                }

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

                return new LoginResponseDTO(
                        "Login successful",
                        authenticationResponse.getName(),
                        type
                );
            }
        }
        throw new InvalidLoginRequestException("Invalid login request");
    }

    private String mapRoleToType(List<String> roles) {
        if (roles.contains("ROLE_MASTERADMIN")) return "MasterAdmin";
        if (roles.contains("ROLE_GOVERNORATEADMIN")) return "GovernorateAdmin";
        if (roles.contains("ROLE_CITYADMIN")) return "CityAdmin";
        if (roles.contains("ROLE_EMPLOYEE")) return "Employee";
        if (roles.contains("ROLE_CITIZEN")) return "Citizen";
        return "Unknown";
    }
}
