package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.InvitationRequest;
import com.coditas.frontline.dto.response.InvitationResponse;
import com.coditas.frontline.entity.Invitation;
import com.coditas.frontline.entity.Users;
import com.coditas.frontline.enums.RoleType;
import com.coditas.frontline.exception.AuthenticationException;
import com.coditas.frontline.repository.CustomUsersRepository;
import com.coditas.frontline.repository.InvitationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static com.coditas.frontline.constants.AuthConstants.*;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;
    private final CustomUsersRepository customUsersRepository;
    private final InvitationRepository invitationRepository;

    public InvitationResponse sendInvitation(@Valid InvitationRequest invitationRequest, Users user) {

        Users invitedBy=customUsersRepository.findByUsername(user.getUsername())
                .orElseThrow(()->new AuthenticationException(USER+NOT_FOUND));


        if(!checkInviteAuthority(user.getRole().name(),invitationRequest.getRole().name())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        String code=UUID.randomUUID().toString().replace("-","").substring(0,6);
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject("Invitation Link");
        simpleMailMessage.setTo(invitationRequest.getEmail());
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setText(String.format(EMAIL_TEXT,INVITATION_LINK,code));
        javaMailSender.send(simpleMailMessage);

        Invitation invitation = Invitation.builder()
                .username(invitationRequest.getEmail())
                .code(code)
                .expireAt(Instant.now().plusSeconds(3000))
                .invitedBy(invitedBy)
                .role(invitationRequest.getRole())
                .build();
        invitationRepository.save(invitation);
        return InvitationResponse.builder()
                .message(EMAIL_SENT)
                .build();
    }

    private boolean checkInviteAuthority(String userRole,String invitedRole ){

        if(Objects.equals(userRole, RoleType.MANAGER.name()) && Objects.equals(invitedRole,RoleType.MANAGER.name())){
            return false;
        }
        if(Objects.equals(userRole,RoleType.AGENT.name()) && Objects.equals(invitedRole,RoleType.MANAGER.name())){
            return false;
        }
        if(Objects.equals(userRole,RoleType.AGENT.name()) && Objects.equals(invitedRole,RoleType.AGENT.name())){
            return false;
        }
       return true;
    }
}
