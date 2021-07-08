package com.kog.mypage.ticket.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TicketType {
    RENTAL("TR"), POSSESSION("TP");

    public final String value;

    public static TicketType toEnum(String value){
        for ( TicketType ticketType : TicketType.values()){
            if (value.equals( ticketType.getValue() ))
                return ticketType;
        }
        throw new IllegalArgumentException();
    }
}
