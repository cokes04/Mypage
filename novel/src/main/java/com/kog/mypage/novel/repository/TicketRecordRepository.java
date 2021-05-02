package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketRecordRepository extends JpaRepository<TicketRecord, Long> {
    List<UseTicketRecord> findByUserAndEpisode(User user, Episode episode);

    @Query("SELECT t FROM AddTicketRecord as t WHERE t.user.id = :userId")
    Page<AddTicketRecord> findAddTicketRecord(Long userId, Pageable pageable);

    @Query("SELECT t FROM UseTicketRecord as t WHERE t.user.id = :userId")
    Page<UseTicketRecord> findUseTicketRecord(Long userId, Pageable pageable);

    @Query("SELECT t FROM AddTicketRecord as t WHERE t.user.id = :userId and t.novel.id = :novelId")
    Page<AddTicketRecord> findAddTicketRecord(Long userId, Long novelId, Pageable pageable);

    @Query("SELECT t FROM UseTicketRecord as t WHERE t.user.id = :userId and t.novel.id = :novelId")
    Page<UseTicketRecord> findUseTicketRecord(Long userId, Long novelId, Pageable pageable);

}
