package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.UserDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    private final CityAdminRepository cityAdminRepository;
    private final GovernorateAdminRepository governorateAdminRepository;
    private final MasterAdminRepository masterAdminRepository;
    private final CitizenRepository citizenRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LoginServiceImpl(CityAdminRepository cityAdminRepository, GovernorateAdminRepository governorateAdminRepository,
                           MasterAdminRepository masterAdminRepository, CitizenRepository cityAdminRepository1,
                           EmployeeRepository employeeRepository) {
        this.cityAdminRepository = cityAdminRepository;
        this.governorateAdminRepository = governorateAdminRepository;
        this.masterAdminRepository = masterAdminRepository;
        this.citizenRepository = cityAdminRepository1;
        this.employeeRepository = employeeRepository;
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
}
