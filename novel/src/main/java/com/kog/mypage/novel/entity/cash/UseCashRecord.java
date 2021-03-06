package com.kog.mypage.novel.entity.cash;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.CashType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;


@Entity
@Getter
@DiscriminatorValue("USE")
public class UseCashRecord extends CashRecord{

    @ManyToOne
    private Novel novel;

    @Builder
    public UseCashRecord(Long id, User user, int amount, CashType cashType, LocalDateTime createdDate, Novel novel) {
        super(id, user, amount, cashType, createdDate);
        this.novel = novel;
    }

}
