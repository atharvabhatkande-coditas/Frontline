package com.coditas.frontline.service;

import com.coditas.frontline.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerAuthService {
    private final CustomerRepository customerRepository;
}
