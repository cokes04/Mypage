package com.kog.mypage.cash.entity;

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

    protected Long userId;

    @Column(nullable = false)
    protected int freeAmount;

    @Column(nullable = false)
    protected int paidAmount;

    @CreatedDate
    protected LocalDateTime createdDate;
}
