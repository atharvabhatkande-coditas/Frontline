package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.ChatOpenRequest;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.Chat;
import com.coditas.frontline.entity.History;
import com.coditas.frontline.entity.Ticket;
import com.coditas.frontline.exception.AlreadyExistException;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.repository.ChatRepository;
import com.coditas.frontline.repository.HistoryRepository;
import com.coditas.frontline.repository.TicketRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.frontline.constants.ExceptionConstants.EXIST;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;
import static com.coditas.frontline.constants.TicketConstants.CHAT;
import static com.coditas.frontline.constants.TicketConstants.TICKET;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final TicketRepository ticketRepository;
    private final HistoryRepository historyRepository;

    public SingleResponse openChat(@Valid ChatOpenRequest chatOpenRequest, UserDetails userDetails) {
        Chat chat=chatRepository.findByTicket_TicketNo(chatOpenRequest.getTicketNo())
                .orElse(null);
        if(!Objects.isNull(chat)){
            throw new AlreadyExistException(CHAT+EXIST);
        }

        Ticket ticket=ticketRepository.findByTicketNo(chatOpenRequest.getTicketNo())
                .orElseThrow(()->new NotFoundException(TICKET+NOT_FOUND));
        List<String> roles=userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String role= roles.getFirst();

        Chat newChat=Chat.builder()
                .ticket(ticket)
                .isActive(true)
                .build();
        chatRepository.save(newChat);

        History history=History.builder()
                .role(role)
                .message(chatOpenRequest.getMessage())
                .chat(newChat)
                .agent()
                .customer()
                .build();

    }
}
