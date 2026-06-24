package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.LoginRequest;
import com.coditas.frontline.dto.request.RegisterRequest;
import com.coditas.frontline.dto.response.AccessTokenResponse;
import com.coditas.frontline.dto.response.LoginResponseTokens;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.Invitation;
import com.coditas.frontline.entity.RefreshToken;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.exception.AlreadyExistException;
import com.coditas.frontline.exception.AuthenticationException;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.repository.CustomUsersRepository;
import com.coditas.frontline.repository.InvitationRepository;
import com.coditas.frontline.repository.RefreshTokenRepository;
import com.coditas.frontline.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

import static com.coditas.frontline.constants.AuthConstants.*;
import static com.coditas.frontline.constants.ExceptionConstants.EXIST;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final CustomUsersRepository  customUsersRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager userAuthenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InvitationRepository invitationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponseTokens loginPlatformUser(LoginRequest request) {
        try{
            Authentication authentication=userAuthenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

            Users user=(Users) authentication.getPrincipal();
            if (Objects.isNull(user)) {
                throw new AuthenticationException(USER+NOT_FOUND);
            }

            LoginResponseTokens loginResponseTokens= jwtUtil.generateTokens(user,"employee");
            RefreshToken refreshToken=RefreshToken.builder()
                    .token(loginResponseTokens.getRefreshToken())
                    .username(user.getUsername())
                    .expireAt(Instant.now().plusSeconds(3000))
                    .build();

            refreshTokenRepository.save(refreshToken);
            return loginResponseTokens;

        }catch (Exception e){
            throw new AuthenticationException(BAD_CREDENTIALS);
        }

    }

    public SingleResponse registerPlatformUser(RegisterRequest registerRequest) {

        Users user=customUsersRepository.findByUsername(registerRequest.getUsername())
                .orElse(null);
        if(!Objects.isNull(user)){
            throw new AlreadyExistException(USER+EXIST);
        }
        Invitation invitation=invitationRepository.findByUsernameAndCode(registerRequest.getUsername(), registerRequest.getCode())
                .orElseThrow(()->new AuthenticationException(VERIFY_CODE));

        if(!Objects.equals(invitation.getCode(),registerRequest.getCode())){
            throw new AuthenticationException(VERIFY_CODE);
        }
        if(invitation.getExpireAt().isBefore(Instant.now())){
            throw new AuthenticationException(VERIFY_CODE);
        }

        Users newUser=Users.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isEnabled(true)
                .role(invitation.getRole())
                .build();
        customUsersRepository.save(newUser);

        return SingleResponse.builder()
                .message(REGISTRATION_SUCCESS)
                .build();

    }

    public AccessTokenResponse generateAccessToken(String token, Users user) {
        RefreshToken refreshToken=refreshTokenRepository.findByUsernameAndToken(user.getUsername(),token)
                .orElseThrow(()-> new NotFoundException(LOGIN_AGAIN));

        if(!Objects.equals(token,refreshToken.getToken())){
            throw new AuthenticationException(LOGIN_AGAIN);
        }
        if(refreshToken.getExpireAt().isBefore(Instant.now())){
            throw new AuthenticationException(LOGIN_AGAIN);
        }

        String accessToken=jwtUtil.generateJwtToken(user.getUsername(),EMPLOYEE,user.getRole());

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public SingleResponse logoutUser(Users user, String token) {
        RefreshToken refreshToken=refreshTokenRepository.findByUsernameAndToken(user.getUsername(),token)
                .orElseThrow(()-> new NotFoundException(LOGIN_AGAIN));

        refreshToken.setToken(null);
        refreshTokenRepository.save(refreshToken);

        return SingleResponse.builder()
                .message(LOGOUT_SUCCESSFUL)
                .build();

    }
}
