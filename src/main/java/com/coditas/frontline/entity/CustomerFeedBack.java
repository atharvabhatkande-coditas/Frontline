package com.coditas.frontline.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer_feedback")
public class CustomerFeedBack extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Tickets ticket;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Double rating;
}
