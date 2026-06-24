package com.coditas.frontline.entity;

import com.coditas.frontline.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tickets")
public class Tickets {
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
}
