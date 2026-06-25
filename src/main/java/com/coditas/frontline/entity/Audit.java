package com.coditas.frontline.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Audit {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
