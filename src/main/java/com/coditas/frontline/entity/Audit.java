package com.coditas.frontline.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EnableJpaAuditing
public abstract class Audit {
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
