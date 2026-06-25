package com.coditas.frontline.entity;

import com.coditas.frontline.enums.Priority;
import com.coditas.frontline.enums.TicketAssignmentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ticket_assignment")
public class TicketAssignment  extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Users agentId;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status")
    private TicketAssignmentStatus ticketAssignmentStatus;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private Users assignedBy;

    @Column(name = "is_current_agent")
    private boolean isCurrentAgent;

}
