package com.coditas.frontline.entity;

import com.coditas.frontline.enums.Priority;
import com.coditas.frontline.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ticket")
public class Ticket extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status")
    private TicketStatus ticketStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "ticket_no")
    private String ticketNo;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;


    @Column(name = "resolution_comment")
    private String resolutionComment;


}
