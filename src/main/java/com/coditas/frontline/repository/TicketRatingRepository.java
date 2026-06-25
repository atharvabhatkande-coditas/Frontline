package com.coditas.frontline.repository;

import com.coditas.frontline.entity.CustomerFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRatingRepository extends JpaRepository<CustomerFeedBack,Long> {
}
