package com.kog.mypage.ticket.repository;

import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.TicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRecordRepository extends JpaRepository<TicketRecord, Long> {
    List<UseTicketRecord> findByEpisodeIdAndUserId(Long episodeId, Long userId);

    @Query("SELECT atr FROM AddTicketRecord as atr WHERE atr.userId = :userId")
    Page<AddTicketRecord> findAddTicketRecord(Long userId, Pageable pageable);

    @Query("SELECT utr FROM UseTicketRecord as utr WHERE utr.userId = :userId")
    Page<UseTicketRecord> findUseTicketRecord(Long userId, Pageable pageable);

    @Query("SELECT atr FROM AddTicketRecord as atr WHERE atr.userId = :userId and atr.novelId = :novelId")
    Page<AddTicketRecord> findAddTicketRecord(Long userId, Long novelId, Pageable pageable);

    @Query("SELECT utr FROM UseTicketRecord as utr WHERE utr.userId = :userId and utr.novelId = :novelId")
    Page<UseTicketRecord> findUseTicketRecord(Long userId, Long novelId, Pageable pageable);

}
