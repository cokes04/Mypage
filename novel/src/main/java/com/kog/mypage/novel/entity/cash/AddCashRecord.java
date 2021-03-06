package com.kog.mypage.novel.entity.cash;

import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.CashType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Setter(value = AccessLevel.PRIVATE)
@Getter
@DiscriminatorValue("ADD")
public class AddCashRecord extends CashRecord{



    @Builder
    public AddCashRecord(Long id, User user, int amount, CashType cashType, LocalDateTime createdDate) {
        super(id, user, amount, cashType, createdDate);
    }
}
