package com.coditas.frontline.service;

import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.repository.CustomUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.coditas.frontline.constants.AuthConstants.USER;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final CustomUsersRepository customUsersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        return customUsersRepository.findByUsername(username)
                .orElseThrow(()->new NotFoundException(USER+NOT_FOUND));
    }
}
