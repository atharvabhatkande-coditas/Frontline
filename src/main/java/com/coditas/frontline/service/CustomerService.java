package com.coditas.frontline.service;

import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.coditas.frontline.constants.AuthConstants.USER;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
       return customerRepository.findByUsername(username)
               .orElseThrow(()->new NotFoundException(USER+NOT_FOUND));
    }
}
