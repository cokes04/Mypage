package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRecordRepository extends JpaRepository<TicketRecord, Long> {
    List<UseTicketRecord> findByUserAndEpisode(User user, Episode episode);
}
