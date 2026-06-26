package com.coditas.frontline.repository;

import com.coditas.frontline.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
    Page<History> findByChat_Id(Long id, Pageable pageable);
}
