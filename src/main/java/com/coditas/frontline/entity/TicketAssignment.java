package com.coditas.frontline.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ticket_assignment")
public class TicketAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Users agentId;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Tickets ticket;
}
