package com.kog.mypage.novel.entity.ticket;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Entity
public abstract class TicketRecord {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @ManyToOne
    @JoinColumn(name = "NOVEL_ID")
    private Novel novel;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TicketType ticketType;

    @Column(nullable = false)
    private int count;

    @CreatedDate
    private LocalDateTime createdDate;

}
