package com.kog.mypage.ticket.repository;


import com.kog.mypage.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

        public Optional<Ticket> findByNovelIdAndUserId(Long novelId, Long userId);

}
