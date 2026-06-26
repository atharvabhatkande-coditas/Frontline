package com.coditas.frontline.service;

import com.coditas.frontline.dto.request.ChatMessageRequest;
import com.coditas.frontline.dto.request.ChatOpenRequest;
import com.coditas.frontline.dto.response.CustomerChatHistoryResponse;
import com.coditas.frontline.dto.response.PageResponse;
import com.coditas.frontline.dto.response.SingleResponse;
import com.coditas.frontline.entity.*;
import com.coditas.frontline.enums.RoleType;
import com.coditas.frontline.exception.AlreadyExistException;
import com.coditas.frontline.exception.AuthorizationException;
import com.coditas.frontline.exception.NotFoundException;
import com.coditas.frontline.mapper.ChatMapper;
import com.coditas.frontline.repository.ChatRepository;
import com.coditas.frontline.repository.HistoryRepository;
import com.coditas.frontline.repository.TicketAssignmentRepository;
import com.coditas.frontline.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.frontline.constants.AuthConstants.UNAUTHORIZED;
import static com.coditas.frontline.constants.ExceptionConstants.EXIST;
import static com.coditas.frontline.constants.ExceptionConstants.NOT_FOUND;
import static com.coditas.frontline.constants.TicketConstants.*;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final TicketRepository ticketRepository;
    private final HistoryRepository historyRepository;
    private final ChatMapper chatMapper;
    private final TicketAssignmentRepository ticketAssignmentRepository;

    @Transactional
    public SingleResponse openChat(ChatOpenRequest chatOpenRequest, UserDetails userDetails) {
        Chat chat=chatRepository.findByTicket_TicketNo(chatOpenRequest.getTicketNo())
                .orElse(null);
        if(!Objects.isNull(chat)){
            throw new AlreadyExistException(CHAT+EXIST);
        }

        Ticket ticket=ticketRepository.findByTicketNo(chatOpenRequest.getTicketNo())
                .orElseThrow(()->new NotFoundException(TICKET+NOT_FOUND));

        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndIsCurrentAgent(chatOpenRequest.getTicketNo(),true)
                .orElseThrow(()->new NotFoundException(TICKET_NOT_ASSIGNED));



        List<String> roles=userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String role= roles.getFirst();


        if(Objects.equals(role,RoleType.AGENT.name()) && !Objects.equals(ticketAssignment.getAgent().getUsername(),userDetails.getUsername())){
            throw new AuthorizationException(UNAUTHORIZED);
        }

        Chat newChat=Chat.builder()
                .ticket(ticket)
                .isActive(true)
                .build();
        chatRepository.save(newChat);

        History history=History.builder()
                .role(role)
                .message(chatOpenRequest.getMessage())
                .chat(newChat)
                .build();


        if(Objects.equals(role, "ROLE_"+RoleType.AGENT.name())){
            history.setCustomer(ticket.getCustomer());
            history.setAgent((Users)userDetails);
        }

        if(Objects.equals(role, "ROLE_"+RoleType.CUSTOMER.name())){
           history.setCustomer((Customer)userDetails );
           history.setAgent(ticketAssignment.getAgent());

        }

        historyRepository.save(history);
        return SingleResponse.builder()
                .message(CHAT_OPENED)
                .build();

    }
    @Transactional
    public SingleResponse message(ChatMessageRequest chatMessageRequest, UserDetails userDetails) {

        Chat chat=chatRepository.findById(chatMessageRequest.getChatId())
                .orElseThrow(()->new NotFoundException(CHAT_NOT_FOUND));

        List<String> roles=userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String role= roles.getFirst();

        History history=History.builder()
                .role(role)
                .message(chatMessageRequest.getMessage())
                .chat(chat)
                .build();


        TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndIsCurrentAgent(chat.getTicket().getTicketNo(),true)
                .orElseThrow(()->new NotFoundException(TICKET_NOT_ASSIGNED));


        if(Objects.equals(role,"ROLE_"+RoleType.AGENT.name())){
            history.setCustomer(chat.getTicket().getCustomer());
            history.setAgent((Users)userDetails);
        }

        if(Objects.equals(role,"ROLE_"+RoleType.CUSTOMER.name())){
            history.setCustomer((Customer)userDetails );
            history.setAgent(ticketAssignment.getAgent());

        }

        historyRepository.save(history);
        return SingleResponse.builder()
                .message(MESSAGE_SENT)
                .build();
    }

    public PageResponse<CustomerChatHistoryResponse> getCustomerChatHistory(String ticketNo, Long customerId, UserDetails userDetails,int page,int size,String name,String sortDirection) {
        Sort sort =  sortDirection.equalsIgnoreCase("asc")
                ?Sort.by(name).ascending()
                : Sort.by(name).descending();
        Pageable pageable= PageRequest.of(page,size,sort);

        Chat chat=chatRepository.findByTicket_TicketNo(ticketNo)
                .orElseThrow(()->new NotFoundException(CHAT+NOT_FOUND));
        List<String> roles=userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String role= roles.getFirst();
        if(Objects.equals(role,"ROLE_"+RoleType.CUSTOMER)){
           Customer customer=(Customer) userDetails;
           if(!Objects.equals(customerId,customer.getId())){
               throw new AuthorizationException(UNAUTHORIZED);
           }
        }
        if(Objects.equals(role,"ROLE_"+RoleType.AGENT)){
            TicketAssignment ticketAssignment=ticketAssignmentRepository.findByTicket_TicketNoAndAgent_Username(ticketNo,userDetails.getUsername())
                    .orElseThrow(()->new NotFoundException(TICKET_NOT_ASSIGNED));
            if(!Objects.equals(ticketNo,ticketAssignment.getTicket().getTicketNo())){
                throw new AuthorizationException(UNAUTHORIZED);
            }

        }



        Page<History>histories=historyRepository.findByChat_Id(chat.getId(),pageable);

        List<CustomerChatHistoryResponse>customerChatHistoryResponses=histories.stream().map(chatMapper::customerChatHistoryResponse).toList();

        return new PageResponse<>(customerChatHistoryResponses,page,size,histories.getTotalElements(),histories.getTotalPages(),histories.isLast());
    }
}
