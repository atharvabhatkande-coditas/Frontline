package com.coditas.frontline.entity;

import com.coditas.frontline.enums.RoleType;
import com.coditas.frontline.enums.TeamType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invitation")
public class Invitation extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "code")
    private String code;

    @Column(name = "expire_at")
    private Instant expireAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleType role;

    @Enumerated(EnumType.STRING)
    @Column(name = "team")
    private TeamType team;

    @ManyToOne
    @JoinColumn(name = "invited_by")
    private Users invitedBy;


}
