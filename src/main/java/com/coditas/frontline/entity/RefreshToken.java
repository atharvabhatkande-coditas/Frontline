package com.coditas.frontline.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "refresh_token")
    private String token;

    @Column(name = "username")
    private String username;

    @Column(name = "expire_at")
    private Instant expireAt;

}
