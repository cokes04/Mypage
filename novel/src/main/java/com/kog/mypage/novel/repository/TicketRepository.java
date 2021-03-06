package com.kog.mypage.novel.repository;


import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    public Optional<Ticket> findByUserAndNovel(User user, Novel novel);
}
