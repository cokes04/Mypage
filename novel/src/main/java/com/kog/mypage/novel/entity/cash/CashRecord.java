package com.kog.mypage.novel.entity.cash;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.CashType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DYPE")
@Entity
public abstract class CashRecord {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    protected User user;

    @Column(nullable = false)
    protected int amount;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(nullable = false)
    protected CashType cashType;

    @CreatedDate
    protected LocalDateTime createdDate;
}
