package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.coditas.frontline.constants.AuthConstants.EMAIL_TEXT;
import static com.coditas.frontline.constants.AuthConstants.INVITATION_LINK;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    public String sendEmail(EmailRequest emailRequest){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject("Update On Your Ticket");
        simpleMailMessage.setTo(emailRequest.getEmail());
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setText(emailRequest.getMessage());
        javaMailSender.send(simpleMailMessage);

        return "Email Sent";
    }
}
