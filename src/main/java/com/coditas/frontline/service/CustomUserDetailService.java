package com.coditas.frontline.service;

import com.coditas.frontline.entity.Customer;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.repository.CustomUsersRepository;
import com.coditas.frontline.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.coditas.frontline.constants.AuthConstants.USER;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final CustomUsersRepository customUsersRepository;
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {

        Users user= customUsersRepository.findByUsername(username)
                .orElse(null);
        Customer customer=customerRepository.findByUsername(username).orElse(null);

        if(Objects.isNull(user) && Objects.isNull(customer)){
            throw new NotFoundException(USER+NOT_FOUND);
        }
        if(!Objects.isNull(user)){
            return user;
        }
        else {
            return customer;
        }
    }
}
