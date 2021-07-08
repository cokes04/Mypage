package com.kog.mypage.ticket.entity;

import com.kog.mypage.ticket.enumeration.TicketType;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorColumn(name = "DTYPE")
@Entity
public abstract class TicketRecord {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long novelId;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TicketType ticketType;  //대여, 소장

    @CreatedDate
    private LocalDateTime createdDate;  // 생성일자

}
